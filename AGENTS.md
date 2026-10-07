# AGENTS.md

## Project requirements (from the instructors)

Berlin Clock exercise. The deliverable is a **public GitHub repository** named `2026-CCE-E-DEV-013-BerlinClock`.

- Backend: **Java + Spring**, developed with a **TDD approach**.
- Frontend: **React** (visual design is not important).
- `README.md` must contain everything needed to compile and run the code. The application must run and fulfil the requirements.
- AI usage is encouraged, but the author must be able to explain every choice made. Produce the best code possible.
- The instructors evaluate the **overall approach**, not only the final code. A single commit with the whole solution, or a solution without tests, will not be reviewed.
- Budget: a few hours maximum.

## TDD workflow (backend)

- One vertical slice at a time: write **one** failing test, then the minimum code to make it pass. Never write all the tests first.
- Tests of the same family may be grouped in one red/green cycle. A family is a set of tests that check **one rule** and that a single implementation makes pass (e.g. both bounds of the same validation: below the minimum and above the maximum). Commit them together in one `[RED]` commit, then one `[GREEN]` commit. Tests that need different logic stay in separate cycles.
- The red test must compile and fail on its assertion (stub returning `null` is fine), not on a missing class.
- Make the green step as dumb as needed (hard-coded value is fine); the next test forces the real logic.
- Refactoring (abstraction, extraction) comes after green, never before the tests that justify it.
- Expected values come from the spec as literals (`"Y"`, `"ROOO"`), never recomputed with the production logic.
- Test names read as a specification: `seconds_lamp_turned_on_when_seconds_even`. One behavior per test.
- Lamps use letters: `Y` yellow, `R` red, `O` off (letter O, not zero).

### Let the author think

The author is learning TDD and wants to work out the solution alone.

- Do **not** suggest the next test to write, nor the expected values or the implementation, unless explicitly asked.
- Review what the author wrote (is the red valid? is the green minimal? are names and expected values consistent with the spec?) and answer questions, but leave the next step to them.
- Writing code or tests for the author is only done on explicit request.
- Only talk about TDD rules (red/green/refactor, test validity, commit convention). Do not add precautions, safeguards, risk warnings or process advice beyond that; the author manages them.

### Commit the TDD steps

Every step of the process must be visible in the history:

- Commit the **red** test on its own (`test(...)`), then the **green** implementation (`feat(...)`), then each refactoring (`refactor(...)`).
- Never squash the steps into one commit.
- Tag the TDD phase in the subject, right after the scope: `[RED]` for the failing test, `[GREEN]` for the implementation that makes it pass. The red and green commits of a cycle keep the **same sentence**; only the tag changes.
- Refactoring commits keep the plain `refactor(...)` type, with no tag.

```
test(backend): [RED] seconds lamp is on when seconds are even
feat(backend): [GREEN] seconds lamp is on when seconds are even
refactor(backend): extract seconds lamp rendering
```

## Git commit convention

Follow [Conventional Commits](https://www.conventionalcommits.org/): `type(scope): description`

- Write commit messages in **English**.
- Subject line: imperative mood, 72 characters max, no trailing period.
- `scope` is optional but recommended: `backend`, `frontend`.
- Body is optional; use it to explain *why*, not *what*. Separate it from the subject with a blank line.
- Prefer small, atomic commits: one logical change per commit.

### Types

| Type       | Use for                                          |
|------------|--------------------------------------------------|
| `feat`     | A new feature                                    |
| `fix`      | A bug fix                                        |
| `refactor` | Code change that neither fixes a bug nor adds a feature |
| `test`     | Adding or updating tests                         |
| `docs`     | Documentation only                               |
| `style`    | Formatting, no code change                       |
| `build`    | Build system or dependencies                     |
| `ci`       | CI configuration                                 |
| `chore`    | Maintenance, scaffolding, tooling                |

### Examples

```
chore(backend): scaffold Spring Boot application
feat(backend): convert time to Berlin Clock representation
test(backend): cover seconds lamp blinking
```
