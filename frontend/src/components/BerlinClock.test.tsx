import { render, screen, within } from '@testing-library/react'
import BerlinClock from './BerlinClock'

const LETTER_BY_LABEL: Record<string, string> = {
  'Yellow lamp': 'Y',
  'Red lamp': 'R',
  'Off lamp': 'O',
}

describe('BerlinClock', () => {
  it('five rows are shown in clock order', () => {
    render(
      <BerlinClock
        berlinClock={{
          seconds: { firstRow: 'Y' },
          hours: { firstRow: 'RRRO', secondRow: 'ROOO' },
          minutes: { firstRow: 'YYRYYROOOOO', secondRow: 'YYYY' },
        }}
      />,
    )

    const rows = screen.getAllByRole('group').map((row) => [
      row.getAttribute('aria-label'),
      within(row)
        .getAllByRole('img')
        .map((lamp) => LETTER_BY_LABEL[lamp.getAttribute('aria-label')!])
        .join(''),
    ])
    expect(rows).toEqual([
      ['Seconds', 'Y'],
      ['Five hours', 'RRRO'],
      ['Hours', 'ROOO'],
      ['Five minutes', 'YYRYYROOOOO'],
      ['Minutes', 'YYYY'],
    ])
  })
})
