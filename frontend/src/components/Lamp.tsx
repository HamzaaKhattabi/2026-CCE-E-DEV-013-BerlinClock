import './Lamp.css'

export type LampIndicator = 'Y' | 'R' | 'O'

type LampProps = {
  indicator: LampIndicator
}

const LAMPS: Record<LampIndicator, { label: string; className: string }> = {
  Y: { label: 'Yellow lamp', className: 'lamp lamp-yellow' },
  R: { label: 'Red lamp', className: 'lamp lamp-red' },
  O: { label: 'Off lamp', className: 'lamp lamp-off' },
}

function Lamp({ indicator }: LampProps) {
  const { label, className } = LAMPS[indicator]

  return <span role="img" aria-label={label} className={className} />
}

export default Lamp
