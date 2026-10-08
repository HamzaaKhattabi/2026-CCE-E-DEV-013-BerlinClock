import { expect, test, type Page } from '@playwright/test'

const LETTER_BY_LABEL: Record<string, string> = {
  'Yellow lamp': 'Y',
  'Red lamp': 'R',
  'Off lamp': 'O',
}

const KATA_EXAMPLES = [
  { time: '00:00:00', lamps: 'YOOOOOOOOOOOOOOOOOOOOOOO' },
  { time: '23:59:59', lamps: 'ORRRRRRROYYRYYRYYRYYYYYY' },
  { time: '16:50:06', lamps: 'YRRROROOOYYRYYRYYRYOOOOO' },
  { time: '11:37:01', lamps: 'ORROOROOOYYRYYRYOOOOYYOO' },
]

async function showBerlinClock(page: Page, time: string) {
  await page.goto('/')
  await page.getByLabel('Time').fill(time)
  await page.getByRole('button', { name: 'Show' }).click()
}

async function readLamps(page: Page) {
  const labels = await page.getByRole('img').evaluateAll((lamps) =>
    lamps.map((lamp) => lamp.getAttribute('aria-label') ?? ''),
  )
  return labels.map((label) => LETTER_BY_LABEL[label]).join('')
}

for (const { time, lamps } of KATA_EXAMPLES) {
  test(`berlin clock shows ${lamps} for ${time}`, async ({ page }) => {
    await showBerlinClock(page, time)
    await expect(page.getByRole('group', { name: 'Seconds' })).toBeVisible()

    expect(await readLamps(page)).toBe(lamps)
  })
}

test('error message from the backend is shown for an invalid time', async ({ page }) => {
  await showBerlinClock(page, 'abc')

  await expect(page.getByRole('alert')).toHaveText('Time must be a valid ISO-8601 time')
  await expect(page.getByRole('img')).toHaveCount(0)
})
