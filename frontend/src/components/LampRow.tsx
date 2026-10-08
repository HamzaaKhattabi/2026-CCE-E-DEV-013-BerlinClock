import { toLampIndicator } from '../lib/lamp'
import Lamp from './Lamp'

type LampRowProps = {
  lamps: string
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
