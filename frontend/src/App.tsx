import { useState, type SubmitEvent } from 'react'
import { fetchBerlinClock, type BerlinClock } from './api/berlinClockApi'

type Result = { berlinClock: BerlinClock } | { error: string } | null

function App() {
  const [result, setResult] = useState<Result>(null)

  // The time is only read on submit, so the input stays uncontrolled:
  // no state or ref to keep in sync, the form already holds the value.
  async function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault()
    const time = new FormData(event.currentTarget).get('time') as string
    try {
      setResult({ berlinClock: await fetchBerlinClock(time) })
    } catch (error) {
      setResult({ error: error instanceof Error ? error.message : 'An unexpected error occurred' })
    }
  }

  return (
    <main>
      <h1>Berlin Clock</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="time">Time</label>
        <input id="time" name="time" />
        <button type="submit">Show</button>
        {result && 'error' in result && <p role="alert">{result.error}</p>}
      </form>
      {result && 'berlinClock' in result && (
        <div>
          <p>{result.berlinClock.seconds.firstRow}</p>
          <p>{result.berlinClock.hours.firstRow}</p>
          <p>{result.berlinClock.hours.secondRow}</p>
          <p>{result.berlinClock.minutes.firstRow}</p>
          <p>{result.berlinClock.minutes.secondRow}</p>
        </div>
      )}
    </main>
  )
}

export default App
