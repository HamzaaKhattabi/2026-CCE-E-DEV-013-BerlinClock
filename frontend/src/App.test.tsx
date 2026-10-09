import { render, screen, within } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'
import { fetchBerlinClock } from './api/berlinClockApi'

vi.mock('./api/berlinClockApi')

describe('App', () => {
  it('berlin clock rows are displayed when a time is submitted', async () => {
    vi.mocked(fetchBerlinClock).mockResolvedValue({
      seconds: { firstRow: 'Y' },
      hours: { firstRow: 'RRRO', secondRow: 'ROOO' },
      minutes: { firstRow: 'YYRYYROOOOO', secondRow: 'YYYY' },
    })
    render(<App />)

    await userEvent.type(screen.getByLabelText('Time'), '16:34:00')
    await userEvent.click(screen.getByRole('button', { name: 'Show' }))

    expect(fetchBerlinClock).toHaveBeenCalledWith('16:34:00')
    const fiveHoursRow = await screen.findByRole('group', { name: 'Five hours' })
    const lamps = within(fiveHoursRow)
      .getAllByRole('img')
      .map((lamp) => lamp.getAttribute('aria-label'))
    expect(lamps).toEqual(['Red lamp', 'Red lamp', 'Red lamp', 'Off lamp'])
  })

  it('error message is displayed when the time is rejected', async () => {
    vi.mocked(fetchBerlinClock).mockRejectedValue(new Error('Time must be a valid ISO-8601 time'))
    render(<App />)

    await userEvent.type(screen.getByLabelText('Time'), 'abc')
    await userEvent.click(screen.getByRole('button', { name: 'Show' }))

    expect(await screen.findByRole('alert')).toHaveTextContent('Time must be a valid ISO-8601 time')
  })

  it('previous berlin clock is cleared when the time is rejected', async () => {
    vi.mocked(fetchBerlinClock)
      .mockResolvedValueOnce({
        seconds: { firstRow: 'Y' },
        hours: { firstRow: 'RRRO', secondRow: 'ROOO' },
        minutes: { firstRow: 'YYRYYROOOOO', secondRow: 'YYYY' },
      })
      .mockRejectedValueOnce(new Error('Time must be a valid ISO-8601 time'))
    render(<App />)
    const timeInput = screen.getByLabelText('Time')
    const showButton = screen.getByRole('button', { name: 'Show' })

    await userEvent.type(timeInput, '16:34:00')
    await userEvent.click(showButton)
    await screen.findByRole('group', { name: 'Five hours' })
    await userEvent.clear(timeInput)
    await userEvent.type(timeInput, 'abc')
    await userEvent.click(showButton)
    await screen.findByRole('alert')

    expect(screen.queryByRole('group', { name: 'Five hours' })).not.toBeInTheDocument()
  })

  it('previous error message is cleared when a valid time is submitted', async () => {
    vi.mocked(fetchBerlinClock)
      .mockRejectedValueOnce(new Error('Time must be a valid ISO-8601 time'))
      .mockResolvedValueOnce({
        seconds: { firstRow: 'Y' },
        hours: { firstRow: 'RRRO', secondRow: 'ROOO' },
        minutes: { firstRow: 'YYRYYROOOOO', secondRow: 'YYYY' },
      })
    render(<App />)
    const timeInput = screen.getByLabelText('Time')
    const showButton = screen.getByRole('button', { name: 'Show' })

    await userEvent.type(timeInput, 'abc')
    await userEvent.click(showButton)
    await screen.findByRole('alert')
    await userEvent.clear(timeInput)
    await userEvent.type(timeInput, '16:34:00')
    await userEvent.click(showButton)
    await screen.findByRole('group', { name: 'Five hours' })

    expect(screen.queryByRole('alert')).not.toBeInTheDocument()
  })

  it('error message is displayed when the clock cannot be shown', async () => {
    vi.mocked(fetchBerlinClock).mockResolvedValue({
      seconds: { firstRow: 'Y' },
      hours: { firstRow: 'RRXO', secondRow: 'ROOO' },
      minutes: { firstRow: 'YYRYYROOOOO', secondRow: 'YYYY' },
    })
    render(<App />)

    await userEvent.type(screen.getByLabelText('Time'), '16:34:00')
    await userEvent.click(screen.getByRole('button', { name: 'Show' }))

    expect(await screen.findByRole('alert')).toHaveTextContent('The clock cannot be displayed')
    expect(screen.getByLabelText('Time')).toBeInTheDocument()
  })
})
