import { fetchBerlinClock } from './berlinClockApi'

describe('fetchBerlinClock', () => {
  afterEach(() => {
    vi.unstubAllGlobals()
  })

  it('berlin clock is returned for the requested time', async () => {
    const berlinClock = {
      seconds: { firstRow: 'Y' },
      hours: { firstRow: 'RRRO', secondRow: 'ROOO' },
      minutes: { firstRow: 'YYRYYROOOOO', secondRow: 'YYYY' },
    }
    const fetchMock = vi.fn().mockResolvedValue(Response.json(berlinClock))
    vi.stubGlobal('fetch', fetchMock)

    await expect(fetchBerlinClock('16:34:00')).resolves.toEqual(berlinClock)
    expect(fetchMock).toHaveBeenCalledWith('/berlin-clock?time=16%3A34%3A00')
  })

  it('error message from the backend is thrown when the time is rejected', async () => {
    const errorResponse = {
      timestamp: '2026-10-09T10:00:00Z',
      path: '/berlin-clock',
      message: 'Time must be a valid ISO-8601 time',
    }
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(Response.json(errorResponse, { status: 400 })))

    await expect(fetchBerlinClock('abc')).rejects.toThrow('Time must be a valid ISO-8601 time')
  })

  it('server unreachable message is thrown when the error response is not json', async () => {
    vi.stubGlobal('fetch', vi.fn().mockResolvedValue(new Response('', { status: 502 })))

    await expect(fetchBerlinClock('16:34:00')).rejects.toThrow('Unable to reach the server')
  })

  it('server unreachable message is thrown when the network fails', async () => {
    vi.stubGlobal('fetch', vi.fn().mockRejectedValue(new TypeError('Failed to fetch')))

    await expect(fetchBerlinClock('16:34:00')).rejects.toThrow('Unable to reach the server')
  })
})
