import java.util.List;

public class BudgetOptimizer {
    public static double calculateAverageSpending(List<Expense> expenses) {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return expenses.isEmpty() ? 0 : total / expenses.size();
    }

    public static void suggestBudget(List<Expense> expenses) {
        double average = calculateAverageSpending(expenses);
        System.out.println("Recommended monthly budget: $" + (average * 30));
    }
}