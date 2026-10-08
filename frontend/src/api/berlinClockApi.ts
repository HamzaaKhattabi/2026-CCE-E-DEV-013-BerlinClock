export type BerlinClock = {
  seconds: { firstRow: string }
  hours: { firstRow: string; secondRow: string }
  minutes: { firstRow: string; secondRow: string }
}

export async function fetchBerlinClock(time: string): Promise<BerlinClock> {
  const response = await fetch(`/berlin-clock?${new URLSearchParams({ time })}`)

  return response.json()
}
