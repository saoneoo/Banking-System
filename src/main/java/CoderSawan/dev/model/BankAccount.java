package CoderSawan.dev.model;


import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private User accountHolder;
    private double balance;
    private boolean isActive;
    private List<String> transactionHistory;

    public BankAccount(String accountNumber, User accountHolder, double initialBalance) {
        // Validation
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (accountHolder == null) {
            throw new IllegalArgumentException("Account holder cannot be null");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.isActive = true;
        this.transactionHistory = new ArrayList<>();

        if (initialBalance > 0) {
            transactionHistory.add("Initial deposit: " + initialBalance);
        }
    }

    public void deposit(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        balance += amount;
        transactionHistory.add("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (!isActive) {
            throw new IllegalStateException("Account is not active");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds. Balance: " + balance);
        }

        balance -= amount;
        transactionHistory.add("Withdrawn: " + amount);
    }

    public void closeAccount() {
        if (!isActive) {
            throw new IllegalStateException("Account is already closed");
        }
        if (balance > 0) {
            throw new IllegalStateException("Cannot close account with remaining balance please withdraw all amounts");
        }

        isActive = false;
        transactionHistory.add("Account closed");
    }

    public void reactivateAccount() {
        if (isActive) {
            throw new IllegalStateException("Account is already active");
        }

        isActive = true;
        transactionHistory.add("Account reactivated");
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public User getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", isActive=" + isActive +
                '}';
    }
}