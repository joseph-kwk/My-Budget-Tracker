import java.io.File
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.security.KeyStore

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

// helper function to safely read a Double
fun readDoubleInput(prompt: String): Double {
    while(true) {
        print(prompt)
        val input = readLine()
        try {
            return input?.toDouble() ?: throw NumberFormatException()
        } catch (e: NumberFormatException) {
            println("Invalid inout. Please enter a valid number.")
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

// Function to save budget data to a CSV file
fun saveBudgetData(filename: String = "budget.csv") {
    try {
        val writer = BufferedWriter(FileWriter(filename))
        for (item in budgetItems) {
            writer.write("${item.description}.${item.amount}.${item.type}\n")
        }
        writer.close()
        println("Budget data saved to $filename")
    } catch (e: IOException) {
        println("Error saving budget data: ${e.message}")
    }
}

// function to load budget data from CSV file
fun loadBudgetData(filename: String = "budget.csv") {
    try {
        val reader = BufferedReader(FileReader(filename))
        var line: String? = reader.readLine()
        while (line != null) {
            val parts = line.split(",")
            if (parts.size  == 3) {
                val description = parts[0]
                val amount = parts[1].toDoubleOrNull() ?: 0.0
                val type = when (parts[2]) {
                    "INCOME" -> EntryType.INCOME
                    "EXPENSE" -> EntryType.EXPENSE
                    else -> EntryType.EXPENSE
                }
                budgetItems.add(BudgetItem(description, amount, type))
            }
            line = reader.readLine()
        }
        reader.close()
        println("Budget data loaded from $filename")
    } catch (e: IOException) {
        println("No existing budget data found or error loading data. Starting with an empty budget.")
    }
}

fun main() {
    // main logic is here
    println("Welcome to My Budget Tracker")

    while (true) {
        println("\n--- Menu ---")
        println("1. Add Income")
        println("2. Add Expense")
        println("3. Show Balance")
        println("4. Save")
        println("5 Exit")
        print("Enter your choice: ")

        when (readLine()) {
            "1" -> addIncome()
            "2" -> addExpense()
            "3" -> showBalance()
            "4" -> saveBudgetData()
            "5" -> {
                saveBudgetData() //save before exit
                println("Exiting Budget Tracker!")
                return
            }
            else -> println("Invalid choice. Please enter a number between 1 and 5")
        }
    }
}