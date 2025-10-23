# Family Tree OOP Project

## Description

A comprehensive in-memory Family Tree application that models people, relationships, and supports various genealogical queries. The application demonstrates Object-Oriented Programming principles and design patterns through a console-based interface.

## Features

- **Person Management**: Add people with detailed attributes (name, gender, birth/death years)
- **Relationship Management**: Establish parent-child relationships and marriages
- **Genealogical Queries**: Find ancestors, descendants, siblings, and spouses
- **Cycle Prevention**: Automatic detection and prevention of relationship cycles
- **Multiple Display Formats**: Indented tree view and line-based output
- **Flexible Traversal**: DFS and BFS traversal strategies for descendants

## OOP Concepts & Design Patterns

### OOP Concepts Applied:

| Concept | Implementation |
|---------|----------------|
| **Encapsulation** | All fields private with getters/setters, validation in setters |
| **Inheritance** | `Person` ← `PersonNode` hierarchy |
| **Polymorphism** | Strategy pattern interfaces with multiple implementations |
| **Abstraction** | `PersonComponent`, `TraversalStrategy`, `Renderer` interfaces |
| **Composition** | `FamilyTree` composes strategies and person collections |

### Design Patterns:

| Pattern | Implementation | Purpose |
|---------|----------------|---------|
| **Composite** | `PersonComponent` + `PersonNode` | Uniform treatment of individual persons and family trees |
| **Strategy** | `TraversalStrategy` + `Renderer` | Pluggable algorithms for traversal and display |
| **Builder** | `PersonBuilder` | Fluent interface for complex Person creation |
| **Factory** | `PersonFactory` | Centralized Person creation with ID generation |

## How to Run

### Compilation:
```bash
javac -d build org/uca/**/*.java org/uca/*.java
```

### Execution:
```bash
java -cp build org.uca.Main
```

## Available Commands

| Command | Usage | Description |
|---------|-------|-------------|
| `ADD_PERSON` | `ADD_PERSON "Full Name" GENDER BIRTH_YEAR [DEATH_YEAR]` | Add a new person |
| `ADD_PARENT_CHILD` | `ADD_PARENT_CHILD PARENT_ID CHILD_ID` | Establish parent-child relationship |
| `MARRY` | `MARRY PERSON1_ID PERSON2_ID YEAR` | Marry two people |
| `ANCESTORS` | `ANCESTORS PERSON_ID GENERATIONS` | Show ancestors up to specified generations |
| `DESCENDANTS` | `DESCENDANTS PERSON_ID GENERATIONS` | Show descendants up to specified generations |
| `SIBLINGS` | `SIBLINGS PERSON_ID` | List all siblings |
| `CHILDREN` | `CHILDREN PERSON_ID` | List direct children |
| `SPOUSE` | `SPOUSE PERSON_ID` | Show current spouse |
| `SHOW` | `SHOW PERSON_ID` | Display person summary |
| `SWITCH_TRAVERSAL` | `SWITCH_TRAVERSAL DFS\|BFS` | Change traversal strategy |
| `SWITCH_RENDERER` | `SWITCH_RENDERER TREE\|LINE` | Change output format |
| `LIST_ALL` | `LIST_ALL` | List all people in system |
| `HELP` | `HELP` | Show command help |
| `EXIT` | `EXIT` | Quit application |

## Sample Session

```
Family Tree CLI - Type 'HELP' for commands, 'EXIT' to quit
Test data loaded successfully!
P001: Aizada Toktobekova
P002: Nurlan Shaidullaev
P003: Aman Shaidullaev

> ADD_PERSON "Bermet Shaidullaeva" FEMALE 2015
-> P004

> ADD_PARENT_CHILD P001 P004
OK

> ADD_PARENT_CHILD P002 P004
OK

> SIBLINGS P004
P003 Aman Shaidullaev (b.2010)

> ANCESTORS P004 2
- P004 Bermet Shaidullaeva (b.2015)
  - P001 Aizada Toktobekova (b.1975)
  - P002 Nurlan Shaidullaev (b.1983)

> DESCENDANTS P001 1
- P001 Aizada Toktobekova (b.1975)
  - P003 Aman Shaidullaev (b.2010)
  - P004 Bermet Shaidullaeva (b.2015)

> SHOW P001
P001 | Aizada Toktobekova | FEMALE | b.1975 | spouse=P002 | children=2

> SWITCH_RENDERER LINE
OK

> DESCENDANTS P001 1
P001 Aizada Toktobekova (b.1975)
P003 Aman Shaidullaev (b.2010)
P004 Bermet Shaidullaeva (b.2015)

> EXIT
```

## Error Handling

The application provides clear error messages for:
- Invalid commands or parameters
- Unknown person IDs
- Relationship cycles
- Adding third parent
- Marrying already married persons
- Invalid years (death before birth, future dates)

## Technical Notes

- **Storage**: In-memory using Java Collections
- **Validation**: Comprehensive input validation with meaningful error messages
- **Performance**: Optimized for families up to ~100 people
- **Extensibility**: Easy to add new traversal strategies or renderers