package CoderSawan.dev.service;


import CoderSawan.dev.model.BankAccount;

public class TransactionService {

    private static final double TRANSACTION_FEE = 5.0;
    private static final double MAX_TRANSFER_AMOUNT = 50000.0;

    public void transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
        // Validation
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Accounts cannot be null");
        }
        if (fromAccount == toAccount) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }
        if (!fromAccount.isActive() || !toAccount.isActive()) {
            throw new IllegalStateException("Both accounts must be active");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (amount > MAX_TRANSFER_AMOUNT) {
            throw new IllegalArgumentException("Transfer amount exceeds maximum limit: " + MAX_TRANSFER_AMOUNT);
        }

        double totalDeduction = amount + TRANSACTION_FEE;

        if (fromAccount.getBalance() < totalDeduction) {
            throw new IllegalArgumentException("Insufficient funds including transaction fee");
        }

        // Perform transfer
        fromAccount.withdraw(amount);
        fromAccount.withdraw(TRANSACTION_FEE); // Deduct fee
        toAccount.deposit(amount);
    }

    public double calculateInterest(BankAccount account, double ratePercent, int months) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (ratePercent < 0 || ratePercent > 100) {
            throw new IllegalArgumentException("Interest rate must be between 0 and 100");
        }
        if (months <= 0) {
            throw new IllegalArgumentException("Months must be positive");
        }

        double principal = account.getBalance();
        double rate = ratePercent / 100.0;
        double time = months / 12.0;

        return principal * rate * time;
    }

    public double getTransactionFee() {
        return TRANSACTION_FEE;
    }

    public double getMaxTransferAmount() {
        return MAX_TRANSFER_AMOUNT;
    }
}