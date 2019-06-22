package com.example.afiqur.tourmatepro.Expense;

/**
 * Created by Istiyak on 07/05/17.
 */
public class Expense {

    String ExpenseId;
    String ExpenseTitle;
    String ExpenseCost;

    public Expense(String expenseId, String expenseTitle, String expenseCost) {
        ExpenseId = expenseId;
        ExpenseTitle = expenseTitle;
        ExpenseCost = expenseCost;
    }

    public String getExpenseId() {
        return ExpenseId;
    }

    public String getExpenseTitle() {
        return ExpenseTitle;
    }

    public String getExpenseCost() {
        return ExpenseCost;
    }
}