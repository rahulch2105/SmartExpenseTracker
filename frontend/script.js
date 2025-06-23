async function addExpense() {
    const category = document.getElementById("category").value;
    const amount = document.getElementById("amount").value;

    await fetch("http://localhost:8080/addExpense", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ category, amount }),
    });

    loadExpenses();
}

async function loadExpenses() {
    const response = await fetch("http://localhost:8080/viewExpenses");
    const expenses = await response.json();

    let tableContent = "";
    expenses.forEach((expense) => {
        tableContent += `<tr>
            <td>${expense.id}</td>
            <td>${expense.category}</td>
            <td>${expense.amount}</td>
            <td><button onclick="deleteExpense(${expense.id})">Delete</button></td>
        </tr>`;
    });

    document.getElementById("expenseTable").innerHTML = tableContent;
}

async function deleteExpense(id) {
    await fetch(`http://localhost:8080/deleteExpense/${id}`, { method: "DELETE" });
    loadExpenses();
}

loadExpenses();