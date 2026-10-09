export type BerlinClockResponse = {
  seconds: { firstRow: string }
  hours: { firstRow: string; secondRow: string }
  minutes: { firstRow: string; secondRow: string }
}

type ErrorResponse = {
  timestamp: string
  path: string
  message: string
}

const UNREACHABLE_SERVER_MESSAGE = 'Unable to reach the server'

export async function fetchBerlinClock(time: string): Promise<BerlinClockResponse> {
  const response = await fetch(`/berlin-clock?${new URLSearchParams({ time })}`).catch(() => {
    throw new Error(UNREACHABLE_SERVER_MESSAGE)
  })

  if (!response.ok) {
    // A proxy answering for a stopped backend sends an empty or HTML body, not an ErrorResponse.
    const errorResponse: ErrorResponse | null = await response.json().catch(() => null)
    throw new Error(errorResponse?.message ?? UNREACHABLE_SERVER_MESSAGE)
  }

  return response.json()
}
