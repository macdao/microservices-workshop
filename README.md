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