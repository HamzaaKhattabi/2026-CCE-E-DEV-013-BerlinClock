import Lamp, { type LampIndicator } from './Lamp'

type LampRowProps = {
  lamps: string
}

const LAMP_INDICATORS = ['Y', 'R', 'O']

function toLampIndicator(letter: string): LampIndicator {
  if (!LAMP_INDICATORS.includes(letter)) {
    throw new Error(`Invalid lamp indicator: ${letter}`)
  }
  return letter as LampIndicator
}

function LampRow({ lamps }: LampRowProps) {
  return (
    <div>
      {[...lamps].map((letter, position) => (
        <Lamp key={position} indicator={toLampIndicator(letter)} />
      ))}
    </div>
  )
}

export default LampRow
