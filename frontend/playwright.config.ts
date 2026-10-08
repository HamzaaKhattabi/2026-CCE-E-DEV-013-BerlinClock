import { defineConfig, devices } from '@playwright/test'

// In Docker, the app already runs in its own containers: BASE_URL points to it
// and no local server is started.
const baseURL = process.env.BASE_URL

export default defineConfig({
  testDir: './e2e',
  use: {
    baseURL: baseURL ?? 'http://localhost:5173',
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
  webServer: baseURL
    ? undefined
    : [
        {
          command: './mvnw spring-boot:run',
          cwd: '../backend',
          url: 'http://localhost:8080/berlin-clock?time=00:00:00',
          reuseExistingServer: true,
          timeout: 120_000,
        },
        {
          command: 'npm run dev',
          url: 'http://localhost:5173',
          reuseExistingServer: true,
        },
      ],
})
