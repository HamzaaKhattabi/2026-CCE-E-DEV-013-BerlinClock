import { render, screen } from '@testing-library/react'
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
    expect(await screen.findByText('Y')).toBeInTheDocument()
    expect(screen.getByText('RRRO')).toBeInTheDocument()
    expect(screen.getByText('ROOO')).toBeInTheDocument()
    expect(screen.getByText('YYRYYROOOOO')).toBeInTheDocument()
    expect(screen.getByText('YYYY')).toBeInTheDocument()
  })

  it('error message is displayed when the time is rejected', async () => {
    vi.mocked(fetchBerlinClock).mockRejectedValue(new Error('Time must be a valid ISO-8601 time'))
    render(<App />)

    await userEvent.type(screen.getByLabelText('Time'), 'abc')
    await userEvent.click(screen.getByRole('button', { name: 'Show' }))

    expect(await screen.findByRole('alert')).toHaveTextContent('Time must be a valid ISO-8601 time')
  })
})
