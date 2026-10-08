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
})
