# Berlin Clock

The Berlin Clock (Mengenlehreuhr) tells the time with 24 lamps arranged in five rows:

| Row | Lamps | Meaning |
|---|---|---|
| Seconds | 1 | Yellow on even seconds, off on odd seconds |
| Five hours | 4 | Each red lamp is 5 hours |
| Hours | 4 | Each red lamp is 1 hour |
| Five minutes | 11 | Each lamp is 5 minutes; every third lamp is red and marks a quarter |
| Minutes | 4 | Each yellow lamp is 1 minute |

Lamps are written with letters: `Y` yellow, `R` red, `O` off (the letter O, not zero).

The solution is a **Java + Spring Boot** backend that converts a time into lamps, and a **React** frontend that displays them. Both were built test-first.

## Prerequisites

| Tool | Version | Needed for |
|---|---|---|
| Java (JDK) | 25 | Backend. Maven is not required: the Maven Wrapper (`mvnw`) downloads it. |
| Node.js | 20.19+ or 22.12+ | Frontend and end-to-end tests |
| Docker | Any recent version with Compose | Optional, only for the Docker option |

Ports `8080` (backend) and `5173` (frontend) must be free.

## Quick start

### Option 1: two terminals

Terminal 1, the backend:

```bash
cd backend
./mvnw spring-boot:run
```

Terminal 2, the frontend:

```bash
cd frontend
npm install
npm run dev
```

Then open <http://localhost:5173>.

On Windows, use `mvnw.cmd` instead of `./mvnw`.

### Option 2: Docker Compose

From the project root, without Java or Node installed:

```bash
docker compose up --build
```

Then open <http://localhost:5173>. Stop everything with `Ctrl+C`, then `docker compose down`.

`--build` makes sure the images match the current code. Without it, Compose reuses images built earlier.

## Using the app

1. Type `16:50:06` in the **Time** field and click **Show**. The clock lights up row by row.
2. Type `abc` and click **Show**. The error message from the backend appears and the clock disappears.
3. Type a valid time again. The error disappears and the new clock is shown.

Accepted formats are the ISO-8601 local times that Java's `LocalTime` reads:

| Input | Shown as |
|---|---|
| `16:50` | 16:50:00 |
| `16:50:06` | 16:50:06 |
| `16:50:06.789` | 16:50:06 (fractions are ignored) |

Rejected inputs return a `400` error: anything that is not a time (`abc`), out-of-range values (`25:00:00`), and `24:00:00`. ISO-8601 allows `24:00:00` to mean "end of day", but a real Berlin Clock goes from 00:00:00 to 23:59:59 and never displays it, and Java's `LocalTime` rejects it too.

## Running the tests

| Suite | Command | What it covers |
|---|---|---|
| Backend | `cd backend && ./mvnw test` | Engines, parser, service, controller, and two tests on the full Spring context |
| Frontend | `cd frontend && npm run test:run` | API client, hook through `App`, and components |
| End to end | see below | The real frontend and backend in a real browser |

`npm test` runs the frontend tests in watch mode, which reruns them on every file change. `npm run test:run` runs them once and exits.

### End-to-end tests

They run the kata examples below and an invalid time through the whole application, with Playwright and Chromium. Playwright starts the backend and the frontend by itself, so stop them first if they are running in other terminals, or leave them on: running servers are reused.

```bash
cd frontend
npm install
npx playwright install chromium
npm run test:e2e
```

`npm install` does not download the browser, hence the second command, needed once.

With Docker instead, without Java, Node or a browser installed:

```bash
docker compose --profile e2e run --rm --build e2e
docker compose --profile e2e down
```

The `e2e` service sits behind a Compose profile, so `docker compose up` only starts the app.

## Building

Backend, a self-contained jar:

```bash
cd backend
./mvnw package
java -jar target/berlin-clock-0.0.1-SNAPSHOT.jar
```

Frontend, static files in `frontend/dist/`:

```bash
cd frontend
npm run build
npm run preview
```

`npm run preview` serves the build on <http://localhost:4173> with the same proxy as `npm run dev`, so it works against the running backend. Served by any other static server, the build needs a proxy that forwards `/berlin-clock` to the backend: that is what nginx does in the Docker image (`frontend/docker/nginx.conf`).

## Kata examples

The examples given with the kata, as one 24-letter string read row by row (seconds, five hours, hours, five minutes, minutes):

| Time | Lamps |
|---|---|
| `00:00:00` | `YOOOOOOOOOOOOOOOOOOOOOOO` |
| `23:59:59` | `ORRRRRRROYYRYYRYYRYYYYYY` |
| `16:50:06` | `YRRROROOOYYRYYRYYRYOOOOO` |
| `11:37:01` | `ORROOROOOYYRYYRYOOOOYYOO` |

The original examples use the digit `0` for an off lamp; this project uses the letter `O` throughout. The API returns the same lamps split into rows, and the end-to-end tests check these four examples through the whole application.
