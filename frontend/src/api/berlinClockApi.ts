export type BerlinClock = {
  seconds: { firstRow: string }
  hours: { firstRow: string; secondRow: string }
  minutes: { firstRow: string; secondRow: string }
}

export async function fetchBerlinClock(_time: string): Promise<BerlinClock> {
  throw new Error('Not implemented')
}
