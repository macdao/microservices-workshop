# Demo

## System Structure

```
[ browser ] - Basic -> [ gateway ] - Plain -> [ consumer ] -> [ provider ]
[ consumer ] -> [ config ]
[ provider] -> [ config ]
[ gateway ] -> [ eureka ]
[ consumer ] -> [ eureka ]
[ provider] -> [ eureka ]
```

```
                                            ┌─────────────────────────────────┐
                                            │                                 ∨
┌─────────┐  Basic   ┌─────────┐  Plain   ┌──────────┐     ┌──────────┐     ┌────────┐
│ browser │ ───────> │ gateway │ ───────> │ consumer │ ──> │ provider │ ──> │ config │
└─────────┘          └─────────┘          └──────────┘     └──────────┘     └────────┘
                       │                    │                │
                       │                    │                │
                       ∨                    │                │
                     ┌─────────┐            │                │
                     │ eureka  │ <──────────┘                │
                     └─────────┘                             │
                       ∧                                     │
                       └─────────────────────────────────────┘
```

## Function

- Eureka. Service registration and discovery
- Config. Centralized external configuration management
- API Gateway with Zuul proxy
- API Gateway using Basic auth with browser
- API Gateway call services with Plain user info
- Runtime config refresh
- API Gateway can create token (by basic) which can be used to replace basic auth via Spring Session