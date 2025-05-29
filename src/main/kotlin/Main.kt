// type of budget entry
enum class EntryType{
    INCOME,
    EXPENSE
}

// single entry in the budget
data class BudgetItem(
    val description: String,
    val amount: Double,
    val type: EntryType
)

// global list to store budget items
val budgetItems = mutableListOf<BudgetItem>()

fun main() {
    // main logic is here
    println("Welcome to My Budget Tracker")

    while (true) {
        println("\n--- Menu ---")
        println("1. Add Income")
        println("2. Add Expense")
        println("3. Show Balance")
        println("4. Exit")
        print("Enter your choice: ")

        when (readLine()) {
            "1" -> addIncome()
            "2" -> addExpense()
            "3" -> showBalance()
            "4" -> {
                println("Exiting Budget Tracker!")
                return
            }
            else -> println("Invalid choice. Please enter a number between 1 and 4")
        }
    }
}

// helper function to safely read a Double from the user
fun readDoubleInput(prompt: String): Double {
    while (true) {
        print(prompt)
        val input = readLine()
        try {
            return input?.toDouble() ?: throw NumberFormatException()
        } catch (e: NumberFormatException){
            println("Invalid input. Please enter a valid number.")
        }
    }
}

fun addIncome() {
    println("\n--- Add Income ---")
    print("Enter description: ")
    val description = readLine().orEmpty()
    val amount = readDoubleInput("Enter amount: ")

    val income = BudgetItem(description, amount, EntryType.INCOME)
    budgetItems.add(income)
    println("Income added successfully")
}

fun addExpense() {
    println("\n--- Add Expense ---")
    print("Enter description: ")
    val description = readLine().orEmpty()
    val amount = readDoubleInput("Enter amount: ")

    val expense = BudgetItem(description, amount, EntryType.EXPENSE)
    budgetItems.add(expense)
    println("Expense added successfully")
}

fun showBalance() {
    println("\n--- Current Balance ---")
    var totalIncome = 0.0
    var totalExpenses = 0.0

    for (item in budgetItems) {
        when (item.type) {
            EntryType.INCOME -> totalIncome += item.amount
            EntryType.EXPENSE -> totalExpenses += item.amount
        }
    }

    val currentBalance = totalIncome - totalExpenses

    println("Total Income: $%.2f" .format(totalIncome))
    println("Total Expenses: $%.2f" .format(totalExpenses))
    println("-----------------")
    println("Current Balance: $%.2f" .format(currentBalance))
    println("--------------\n")
}