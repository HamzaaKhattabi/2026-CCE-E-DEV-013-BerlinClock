# AGENTS.md

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
