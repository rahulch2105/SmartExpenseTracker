import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
    private static final String USER = "root";
    private static final String PASSWORD = "Saikumarch0909@";

    public void addExpense(String category, double amount) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO expenses (category, amount) VALUES (?, ?)")) {
            stmt.setString(1, category);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> viewExpenses() {
        List<String> expenses = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM expenses")) {
            while (rs.next()) {
                expenses.add("{ \"id\": " + rs.getInt("id") +
                        ", \"category\": \"" + rs.getString("category") +
                        "\", \"amount\": " + rs.getDouble("amount") + " }");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    public void deleteExpense(int id) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM expenses WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}