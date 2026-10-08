import Lamp, { type LampIndicator } from './Lamp'

type LampRowProps = {
  lamps: string
}

function LampRow({ lamps }: LampRowProps) {
  return (
    <div>
      {[...lamps].map((indicator, position) => (
        <Lamp key={position} indicator={indicator as LampIndicator} />
      ))}
    </div>
  )
}

export default LampRow
