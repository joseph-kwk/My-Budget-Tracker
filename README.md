# My Budget Tracker

A small command-line budget tracker written in Kotlin. It lets you add incomes and expenses, view the current balance, and persist entries to a CSV file (`budget.csv`). This project is intentionally simple and useful as a learning/example Kotlin CLI app.

## Features

- Add income entries with a description and amount
- Add expense entries with a description and amount
- Show totals: total income, total expenses, and current balance
- Save and load entries from `budget.csv`

## Requirements

- JDK 11 or later
- Gradle (the project includes the Gradle wrapper so you don't need a system Gradle)
- (Optional) IntelliJ IDEA or another Kotlin-capable IDE for easy running/debugging

## Build

From the project root (Windows PowerShell):

```powershell
.\gradlew.bat build
```

This compiles the project and runs tests (if any).

## Run

There are two recommended ways to run the program.

1) From your IDE

- Open the folder in IntelliJ IDEA and run `src/main/kotlin/Main.kt` (the `main` function).

2) From the command line using the Gradle wrapper

```powershell
.\gradlew.bat run    # may require the 'application' plugin; see notes below
```

If `run` is not configured, you can still execute the compiled classes from the IDE or configure the `application` plugin in `build.gradle.kts`:

```kotlin
plugins {
  kotlin("jvm") version "2.1.20"
  application
}

application {
  // If your Main.kt has no package, the generated class name is MainKt
  mainClass.set("MainKt")
}
```

After adding the `application` plugin you can use `.\gradlew.bat run`.

## Usage

When you run the app a small text menu appears:

1. Add Income — prompts for description and amount
2. Add Expense — prompts for description and amount
3. Show Balance — prints total income, total expenses, and current balance
4. Save — write current entries to `budget.csv`
5. Exit — saves automatically then exits

Example session:

```
Welcome to My Budget Tracker

--- Menu ---
1. Add Income
2. Add Expense
3. Show Balance
4. Save
5 Exit
Enter your choice: 1
Enter description: Salary
Enter amount: 2500
Income added successfully
```

## Data format (`budget.csv`)

Each entry is stored as a CSV row with three fields:

- description (string)
- amount (number)
- type (INCOME or EXPENSE)

Example valid line:

```
Salary,2500.0,INCOME
```

Note: The current code attempts to read/write `budget.csv`, but there is a mismatch in the delimiter used when saving vs loading (see Known issues below).

## Known issues and suggested fix

- Saving uses `.` as the field separator while loading expects `,` (comma). This causes saved data not to load correctly.

Suggested minimal fix in `src/main/kotlin/Main.kt` (change `saveBudgetData` writer line):

```kotlin
// replace this line
writer.write("${item.description}.${item.amount}.${item.type}\n")

// with this line
writer.write("${item.description},${item.amount},${item.type}\n")
```

That change makes save/load use the same comma-separated format.

## Tests

There are no project unit tests at the moment. You can run `.\gradlew.bat build` to compile the project and run the default test task (if/when tests are added).

## Contributing

Contributions are welcome. Small, focused PRs are easiest to review. Suggested next improvements:

- Fix the save/load delimiter bug (see above)
- Add input validation and friendly error handling
- Add persistence format choices (CSV, JSON)
- Add tests for save/load and balance calculations

## License

This repository doesn't include an explicit license file. Add a `LICENSE` file if you want to make the intended license explicit.

## Contact / Author

If you want help expanding this README or adding features, open an issue or send a PR with suggested changes.
