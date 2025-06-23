import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Route for viewing expenses
        server.createContext("/expenses", new ViewExpensesHandler());
        
        // Route for adding an expense
        server.createContext("/addExpense", new AddExpenseHandler());

        // Route for deleting an expense
        server.createContext("/deleteExpense", new DeleteExpenseHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080...");
    }
}

// Handle GET request to view expenses
class ViewExpensesHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            ExpenseDAO expenseDAO = new ExpenseDAO();
            List<String> expenses = expenseDAO.viewExpenses();

            String response = expenses.toString();
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

// Handle POST request to add an expense
class AddExpenseHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String[] data = reader.readLine().split(",");
            
            String category = data[0].split(":")[1].replace("\"", "").trim();
            double amount = Double.parseDouble(data[1].split(":")[1].replace("}", "").trim());

            ExpenseDAO expenseDAO = new ExpenseDAO();
            expenseDAO.addExpense(category, amount);

            String response = "Expense added successfully.";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

// Handle DELETE request to remove an expense
class DeleteExpenseHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            int id = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);

            ExpenseDAO expenseDAO = new ExpenseDAO();
            expenseDAO.deleteExpense(id);

            String response = "Expense deleted successfully.";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}