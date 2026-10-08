import { render, screen } from '@testing-library/react'
import LampRow from './LampRow'

describe('LampRow', () => {
  it('one lamp is shown per indicator in the row order', () => {
    render(<LampRow lamps="RRYO" />)

    const lamps = screen.getAllByRole('img').map((lamp) => lamp.getAttribute('aria-label'))
    expect(lamps).toEqual(['Red lamp', 'Red lamp', 'Yellow lamp', 'Off lamp'])
  })

  it('error is thrown when an indicator is unknown', () => {
    expect(() => render(<LampRow lamps="RRXO" />)).toThrow('Invalid lamp indicator: X')
  })
})
