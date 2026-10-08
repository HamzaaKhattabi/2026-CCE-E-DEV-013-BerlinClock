export const LAMP_INDICATORS = ['Y', 'R', 'O'] as const

export type LampIndicator = (typeof LAMP_INDICATORS)[number]

export function toLampIndicator(letter: string): LampIndicator {
  if (!LAMP_INDICATORS.includes(letter as LampIndicator)) {
    throw new Error(`Invalid lamp indicator: ${letter}`)
  }
  return letter as LampIndicator
}
