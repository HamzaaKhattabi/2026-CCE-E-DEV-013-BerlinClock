import { useState } from 'react'
import { fetchBerlinClock, type BerlinClockResponse } from '../api/berlinClockApi'

type Result = { berlinClock: BerlinClockResponse } | { error: string } | null

export function useBerlinClock() {
  const [result, setResult] = useState<Result>(null)

  async function showBerlinClock(time: string) {
    try {
      setResult({ berlinClock: await fetchBerlinClock(time) })
    } catch (error) {
      setResult({ error: error instanceof Error ? error.message : 'An unexpected error occurred' })
    }
  }

  return {
    berlinClock: result && 'berlinClock' in result ? result.berlinClock : null,
    error: result && 'error' in result ? result.error : null,
    showBerlinClock,
  }
}
