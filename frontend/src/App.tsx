import type { SubmitEvent } from 'react'
import BerlinClock from './components/BerlinClock'
import ErrorBoundary from './components/ErrorBoundary'
import { useBerlinClock } from './hooks/useBerlinClock'

function App() {
  const { berlinClock, error, showBerlinClock } = useBerlinClock()

  // The time is only read on submit, so the input stays uncontrolled:
  // no state or ref to keep in sync, the form already holds the value.
  function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault()
    showBerlinClock(new FormData(event.currentTarget).get('time') as string)
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
        <ErrorBoundary fallback={<p role="alert">The clock cannot be displayed</p>}>
          <BerlinClock berlinClock={berlinClock} />
        </ErrorBoundary>
      )}
    </main>
  )
}

export default App
