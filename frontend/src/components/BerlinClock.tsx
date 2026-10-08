import type { BerlinClockResponse } from '../api/berlinClockApi'
import LampRow from './LampRow'

type BerlinClockProps = {
  berlinClock: BerlinClockResponse
}

function BerlinClock({ berlinClock }: BerlinClockProps) {
  const rows = [
    { label: 'Seconds', lamps: berlinClock.seconds.firstRow },
    { label: 'Five hours', lamps: berlinClock.hours.firstRow },
    { label: 'Hours', lamps: berlinClock.hours.secondRow },
    { label: 'Five minutes', lamps: berlinClock.minutes.firstRow },
    { label: 'Minutes', lamps: berlinClock.minutes.secondRow },
  ]

  return (
    <div>
      {rows.map(({ label, lamps }) => (
        <div key={label} role="group" aria-label={label}>
          <LampRow lamps={lamps} />
        </div>
      ))}
    </div>
  )
}

export default BerlinClock
