package CoderSawan.dev.validator;


public class AccountValidator {

    private static final int MIN_ACCOUNT_NUMBER_LENGTH = 8;
    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 12;

    public boolean isValidAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            return false;
        }

        accountNumber = accountNumber.trim();

        if (accountNumber.length() < MIN_ACCOUNT_NUMBER_LENGTH ||
                accountNumber.length() > MAX_ACCOUNT_NUMBER_LENGTH) {
            return false;
        }

        // Check if contains only digits
        return accountNumber.matches("\\d+");
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }

        email = email.trim();

        // Basic email validation
        return email.contains("@") &&
                email.indexOf("@") > 0 &&
                email.indexOf("@") < email.length() - 1;
    }

    public boolean isValidAge(int age) {
        return age >= 18 && age <= 120;
    }

    public boolean canOpenAccount(int age, double initialBalance) {
        return isValidAge(age) && initialBalance >= 0;
    }
}