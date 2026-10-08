import { useState, type SubmitEvent } from 'react'
import { fetchBerlinClock, type BerlinClock } from './api/berlinClockApi'

function App() {
  const [berlinClock, setBerlinClock] = useState<BerlinClock | null>(null)
  const [error, setError] = useState<string | null>(null)

  // The time is only read on submit, so the input stays uncontrolled:
  // no state or ref to keep in sync, the form already holds the value.
  async function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault()
    const time = new FormData(event.currentTarget).get('time') as string
    try {
      setBerlinClock(await fetchBerlinClock(time))
      setError(null)
    } catch (error) {
      setBerlinClock(null)
      setError((error as Error).message)
    }
  }

  return (
    <main>
      <h1>Berlin Clock</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="time">Time</label>
        <input id="time" name="time" />
        <button type="submit">Show</button>
        {error && <p role="alert">{error}</p>}
      </form>
      {berlinClock && (
        <div>
          <p>{berlinClock.seconds.firstRow}</p>
          <p>{berlinClock.hours.firstRow}</p>
          <p>{berlinClock.hours.secondRow}</p>
          <p>{berlinClock.minutes.firstRow}</p>
          <p>{berlinClock.minutes.secondRow}</p>
        </div>
      )}
    </main>
  )
}

export default App
