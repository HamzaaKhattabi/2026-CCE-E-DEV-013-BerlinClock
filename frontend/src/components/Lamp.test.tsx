import { render, screen } from '@testing-library/react'
import Lamp from './Lamp'

describe('Lamp', () => {
  it('yellow lamp is shown when the indicator is Y', () => {
    render(<Lamp indicator="Y" />)

    expect(screen.getByRole('img', { name: 'Yellow lamp' })).toBeInTheDocument()
  })

  it('red lamp is shown when the indicator is R', () => {
    render(<Lamp indicator="R" />)

    expect(screen.getByRole('img', { name: 'Red lamp' })).toBeInTheDocument()
  })

  it('off lamp is shown when the indicator is O', () => {
    render(<Lamp indicator="O" />)

    expect(screen.getByRole('img', { name: 'Off lamp' })).toBeInTheDocument()
  })
})
