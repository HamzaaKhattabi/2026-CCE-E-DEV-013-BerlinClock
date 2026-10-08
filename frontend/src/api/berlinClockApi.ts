export type BerlinClock = {
  seconds: { firstRow: string }
  hours: { firstRow: string; secondRow: string }
  minutes: { firstRow: string; secondRow: string }
}

type ErrorResponse = {
  timestamp: string
  path: string
  message: string
}

export async function fetchBerlinClock(time: string): Promise<BerlinClock> {
  const response = await fetch(`/berlin-clock?${new URLSearchParams({ time })}`)

  if (!response.ok) {
    const errorResponse: ErrorResponse = await response.json()
    throw new Error(errorResponse.message)
  }

  return response.json()
}
