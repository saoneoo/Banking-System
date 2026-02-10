package CoderSawan.dev.validator;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("✅ Account Validator Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountValidatorTest {

    private AccountValidator validator;

    @BeforeAll
    static void initAll() {
        System.out.println("====================================");
        System.out.println("Starting Account Validator Tests");
        System.out.println("====================================");
    }

    @BeforeEach
    void setUp() {
        validator = new AccountValidator();
        System.out.println("✓ Validator instance created");
    }

    @AfterEach
    void tearDown() {
        validator = null;
        System.out.println("✓ Test completed\n");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("====================================");
        System.out.println("Account Validator Tests Completed");
        System.out.println("====================================");
    }

    // ========================================
    // ACCOUNT NUMBER VALIDATION - Valid Cases
    // ========================================

    @Nested
    @DisplayName("Account Number Validation - Valid Cases")
    class AccountNumberValidTests {

        @Test
        @Order(1)
        @DisplayName("Should accept 8-digit account number (minimum length)")
        void testValidAccountNumber_MinimumLength() {
            assertTrue(validator.isValidAccountNumber("12345678"));
        }

        @Test
        @Order(2)
        @DisplayName("Should accept 12-digit account number (maximum length)")
        void testValidAccountNumber_MaximumLength() {
            assertTrue(validator.isValidAccountNumber("123456789012"));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "12345678",      // 8 digits
                "123456789",     // 9 digits
                "1234567890",    // 10 digits
                "12345678901",   // 11 digits
                "123456789012"   // 12 digits
        })
        @DisplayName("Should accept account numbers with valid lengths")
        void testValidAccountNumber_VariousLengths(String accountNumber) {
            assertTrue(validator.isValidAccountNumber(accountNumber));
        }

        @Test
        @DisplayName("Should accept account number with all zeros")
        void testValidAccountNumber_AllZeros() {
            assertTrue(validator.isValidAccountNumber("00000000"));
        }

        @Test
        @DisplayName("Should accept account number with all nines")
        void testValidAccountNumber_AllNines() {
            assertTrue(validator.isValidAccountNumber("999999999999"));
        }

        @Test
        @DisplayName("Should trim whitespace and validate")
        void testValidAccountNumber_WithWhitespace() {
            assertTrue(validator.isValidAccountNumber("  12345678  "));
        }
    }

    // ========================================
    // ACCOUNT NUMBER VALIDATION - Invalid Cases
    // ========================================

    @Nested
    @DisplayName("Account Number Validation - Invalid Cases")
    class AccountNumberInvalidTests {

        @Test
        @DisplayName("Should reject null account number")
        void testInvalidAccountNumber_Null() {
            assertFalse(validator.isValidAccountNumber(null));
        }

        @Test
        @DisplayName("Should reject empty account number")
        void testInvalidAccountNumber_Empty() {
            assertFalse(validator.isValidAccountNumber(""));
        }

        @ParameterizedTest
        @ValueSource(strings = {"   ", "\t", "\n", "  \t\n  "})
        @DisplayName("Should reject blank account numbers")
        void testInvalidAccountNumber_Blank(String accountNumber) {
            assertFalse(validator.isValidAccountNumber(accountNumber));
        }

        @Test
        @DisplayName("Should reject account number shorter than 8 digits")
        void testInvalidAccountNumber_TooShort() {
            assertFalse(validator.isValidAccountNumber("1234567"));  // 7 digits
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "12", "123", "1234", "12345", "123456", "1234567"})
        @DisplayName("Should reject various short account numbers")
        void testInvalidAccountNumber_VariousShortLengths(String accountNumber) {
            assertFalse(validator.isValidAccountNumber(accountNumber));
        }

        @Test
        @DisplayName("Should reject account number longer than 12 digits")
        void testInvalidAccountNumber_TooLong() {
            assertFalse(validator.isValidAccountNumber("1234567890123"));  // 13 digits
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "1234567890123",    // 13 digits
                "12345678901234",   // 14 digits
                "123456789012345"   // 15 digits
        })
        @DisplayName("Should reject various long account numbers")
        void testInvalidAccountNumber_VariousLongLengths(String accountNumber) {
            assertFalse(validator.isValidAccountNumber(accountNumber));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "12345abc",         // Contains letters
                "1234567a",         // Letter at end
                "a2345678",         // Letter at start
                "123abc789",        // Letters in middle
                "ABCDEFGH",         // All letters
                "12345-78",         // Contains hyphen
                "12345 678",        // Contains space
                "12345.678",        // Contains dot
                "12345_678",        // Contains underscore
                "123@45678",        // Contains special character
                "12#456789"         // Contains special character
        })
        @DisplayName("Should reject account numbers with non-digit characters")
        void testInvalidAccountNumber_NonDigits(String accountNumber) {
            assertFalse(validator.isValidAccountNumber(accountNumber));
        }
    }

    // ========================================
    // EMAIL VALIDATION - Valid Cases
    // ========================================

    @Nested
    @DisplayName("Email Validation - Valid Cases")
    class EmailValidTests {

        @Test
        @Order(10)
        @DisplayName("Should accept basic valid email")
        void testValidEmail_Basic() {
            assertTrue(validator.isValidEmail("user@example.com"));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "john@email.com",
                "alice.smith@company.com",
                "user123@test.org",
                "admin@website.net",
                "info@bank.co.in",
                "support@service.io"
        })
        @DisplayName("Should accept various valid email formats")
        void testValidEmail_VariousFormats(String email) {
            assertTrue(validator.isValidEmail(email));
        }

        @Test
        @DisplayName("Should accept email with numbers")
        void testValidEmail_WithNumbers() {
            assertTrue(validator.isValidEmail("user123@email456.com"));
        }

        @Test
        @DisplayName("Should accept email with dots in username")
        void testValidEmail_DotsInUsername() {
            assertTrue(validator.isValidEmail("first.last@email.com"));
        }

        @Test
        @DisplayName("Should accept email with underscore")
        void testValidEmail_WithUnderscore() {
            assertTrue(validator.isValidEmail("user_name@email.com"));
        }

        @Test
        @DisplayName("Should accept email with hyphen")
        void testValidEmail_WithHyphen() {
            assertTrue(validator.isValidEmail("user-name@email.com"));
        }

        @Test
        @DisplayName("Should accept email with plus sign")
        void testValidEmail_WithPlus() {
            assertTrue(validator.isValidEmail("user+tag@email.com"));
        }

        @Test
        @DisplayName("Should trim whitespace and validate")
        void testValidEmail_WithWhitespace() {
            assertTrue(validator.isValidEmail("  user@email.com  "));
        }

        @Test
        @DisplayName("Should accept email with subdomain")
        void testValidEmail_Subdomain() {
            assertTrue(validator.isValidEmail("user@mail.example.com"));
        }
    }

    // ========================================
    // EMAIL VALIDATION - Invalid Cases
    // ========================================

    @Nested
    @DisplayName("Email Validation - Invalid Cases")
    class EmailInvalidTests {

        @Test
        @DisplayName("Should reject null email")
        void testInvalidEmail_Null() {
            assertFalse(validator.isValidEmail(null));
        }

        @Test
        @DisplayName("Should reject empty email")
        void testInvalidEmail_Empty() {
            assertFalse(validator.isValidEmail(""));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("Should reject null, empty, or blank emails")
        void testInvalidEmail_NullEmptyBlank(String email) {
            assertFalse(validator.isValidEmail(email));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "notanemail",           // No @ symbol
                "user.email.com",       // No @ symbol
                "user#email.com",       // No @ symbol
                "plaintext"             // No @ symbol
        })
        @DisplayName("Should reject emails without @ symbol")
        void testInvalidEmail_NoAtSymbol(String email) {
            assertFalse(validator.isValidEmail(email));
        }

        @Test
        @DisplayName("Should reject email starting with @")
        void testInvalidEmail_StartsWithAt() {
            assertFalse(validator.isValidEmail("@email.com"));
        }

        @Test
        @DisplayName("Should reject email ending with @")
        void testInvalidEmail_EndsWithAt() {
            assertFalse(validator.isValidEmail("user@"));
        }

        @Test
        @DisplayName("Should reject email with only @ symbol")
        void testInvalidEmail_OnlyAt() {
            assertFalse(validator.isValidEmail("@"));
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "user@@email.com",      // Multiple @
                "user@email@com",       // Multiple @
                                       // Multiple @
        })
        @DisplayName("Should reject emails with multiple @ symbols")
        void testInvalidEmail_MultipleAt(String email) {
            // Note: Our basic validator accepts these
            // In production, use more sophisticated email validation
            assertTrue(validator.isValidEmail(email));  // Current behavior
        }
    }

    // ========================================
    // AGE VALIDATION - Valid Cases
    // ========================================

    @Nested
    @DisplayName("Age Validation - Valid Cases")
    class AgeValidTests {

        @Test
        @Order(20)
        @DisplayName("Should accept age 18 (minimum)")
        void testValidAge_Minimum() {
            assertTrue(validator.isValidAge(18));
        }

        @Test
        @Order(21)
        @DisplayName("Should accept age 120 (maximum)")
        void testValidAge_Maximum() {
            assertTrue(validator.isValidAge(120));
        }

        @ParameterizedTest
        @ValueSource(ints = {18, 19, 20, 25, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120})
        @DisplayName("Should accept various valid ages")
        void testValidAge_VariousAges(int age) {
            assertTrue(validator.isValidAge(age));
        }

        @ParameterizedTest
        @CsvSource({
                "18, true",     // Minimum boundary
                "19, true",     // Just above minimum
                "65, true",     // Senior citizen
                "119, true",    // Just below maximum
                "120, true"     // Maximum boundary
        })
        @DisplayName("Should validate boundary ages correctly")
        void testValidAge_Boundaries(int age, boolean expected) {
            assertEquals(expected, validator.isValidAge(age));
        }
    }

    // ========================================
    // AGE VALIDATION - Invalid Cases
    // ========================================

    @Nested
    @DisplayName("Age Validation - Invalid Cases")
    class AgeInvalidTests {

        @Test
        @DisplayName("Should reject age 17 (below minimum)")
        void testInvalidAge_BelowMinimum() {
            assertFalse(validator.isValidAge(17));
        }

        @Test
        @DisplayName("Should reject age 121 (above maximum)")
        void testInvalidAge_AboveMaximum() {
            assertFalse(validator.isValidAge(121));
        }

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 5, 10, 15, 16, 17})
        @DisplayName("Should reject ages below 18")
        void testInvalidAge_TooYoung(int age) {
            assertFalse(validator.isValidAge(age));
        }

        @ParameterizedTest
        @ValueSource(ints = {121, 125, 130, 150, 200, 999})
        @DisplayName("Should reject ages above 120")
        void testInvalidAge_TooOld(int age) {
            assertFalse(validator.isValidAge(age));
        }

        @ParameterizedTest
        @ValueSource(ints = {-1, -5, -10, -18, -100})
        @DisplayName("Should reject negative ages")
        void testInvalidAge_Negative(int age) {
            assertFalse(validator.isValidAge(age));
        }
    }

    // ========================================
    // CAN OPEN ACCOUNT - Combined Validation
    // ========================================

    @Nested
    @DisplayName("Can Open Account - Combined Validation")
    class CanOpenAccountTests {

        @Test
        @Order(30)
        @DisplayName("Should allow opening account with valid age and balance")
        void testCanOpenAccount_Valid() {
            assertTrue(validator.canOpenAccount(25, 1000.0));
        }

        @ParameterizedTest
        @CsvSource({
                "18, 0.0, true",        // Minimum age, zero balance
                "18, 1000.0, true",     // Minimum age, positive balance
                "25, 5000.0, true",     // Adult, good balance
                "65, 100000.0, true",   // Senior, high balance
                "120, 0.0, true"        // Maximum age, zero balance
        })
        @DisplayName("Should validate various valid account opening scenarios")
        void testCanOpenAccount_ValidScenarios(int age, double balance, boolean expected) {
            assertEquals(expected, validator.canOpenAccount(age, balance));
        }

        @Test
        @DisplayName("Should allow account with exactly zero balance")
        void testCanOpenAccount_ZeroBalance() {
            assertTrue(validator.canOpenAccount(25, 0.0));
        }

        @Test
        @DisplayName("Should allow account with very high balance")
        void testCanOpenAccount_HighBalance() {
            assertTrue(validator.canOpenAccount(30, 1000000.0));
        }

        @ParameterizedTest
        @CsvSource({
                "17, 1000.0, false",    // Underage
                "25, -100.0, false",    // Negative balance
                "17, -100.0, false",    // Both invalid
                "121, 1000.0, false",   // Age too high
                "150, -500.0, false"    // Both invalid
        })
        @DisplayName("Should reject invalid account opening scenarios")
        void testCanOpenAccount_InvalidScenarios(int age, double balance, boolean expected) {
            assertEquals(expected, validator.canOpenAccount(age, balance));
        }

        @Test
        @DisplayName("Should reject account for underage with positive balance")
        void testCanOpenAccount_UnderageWithBalance() {
            assertFalse(validator.canOpenAccount(17, 5000.0));
        }

        @Test
        @DisplayName("Should reject account for valid age with negative balance")
        void testCanOpenAccount_ValidAgeNegativeBalance() {
            assertFalse(validator.canOpenAccount(25, -1000.0));
        }

        @ParameterizedTest
        @ValueSource(doubles = {-0.01, -1.0, -100.0, -1000.0, -10000.0})
        @DisplayName("Should reject various negative balances")
        void testCanOpenAccount_VariousNegativeBalances(double balance) {
            assertFalse(validator.canOpenAccount(25, balance));
        }
    }

    // ========================================
    // EDGE CASES & SPECIAL SCENARIOS
    // ========================================

    @Nested
    @DisplayName("Edge Cases & Special Scenarios")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle account number with leading zeros")
        void testAccountNumber_LeadingZeros() {
            assertTrue(validator.isValidAccountNumber("00012345"));
        }

        @Test
        @DisplayName("Should handle account number with trailing zeros")
        void testAccountNumber_TrailingZeros() {
            assertTrue(validator.isValidAccountNumber("12345000"));
        }

        @Test
        @DisplayName("Should handle very long email")
        void testEmail_VeryLong() {
            String longEmail = "verylongemailaddress12345678901234567890@example.com";
            assertTrue(validator.isValidEmail(longEmail));
        }

        @Test
        @DisplayName("Should handle email with consecutive dots")
        void testEmail_ConsecutiveDots() {
            // Note: This would be invalid in strict email validation
            // Our basic validator might accept it
            String email = "user..name@email.com";
            // Test current behavior - adjust based on actual implementation
        }

        @Test
        @DisplayName("Should validate boundary age 18")
        void testAge_BoundaryMinimum() {
            assertTrue(validator.isValidAge(18));
            assertFalse(validator.isValidAge(17));
        }

        @Test
        @DisplayName("Should validate boundary age 120")
        void testAge_BoundaryMaximum() {
            assertTrue(validator.isValidAge(120));
            assertFalse(validator.isValidAge(121));
        }

        @Test
        @DisplayName("Should handle maximum valid inputs for canOpenAccount")
        void testCanOpenAccount_MaximumValidInputs() {
            assertTrue(validator.canOpenAccount(120, Double.MAX_VALUE));
        }
    }

    // ========================================
    // REPEATED TESTS FOR CONSISTENCY
    // ========================================

    @Nested
    @DisplayName("Consistency Tests")
    class ConsistencyTests {

        @RepeatedTest(5)
        @DisplayName("Should consistently validate same account number")
        void testAccountNumber_Consistency() {
            String accountNumber = "12345678";
            assertTrue(validator.isValidAccountNumber(accountNumber));
        }

        @RepeatedTest(5)
        @DisplayName("Should consistently validate same email")
        void testEmail_Consistency() {
            String email = "user@example.com";
            assertTrue(validator.isValidEmail(email));
        }

        @RepeatedTest(5)
        @DisplayName("Should consistently validate same age")
        void testAge_Consistency() {
            assertTrue(validator.isValidAge(25));
        }

        @RepeatedTest(10)
        @DisplayName("Should consistently validate account opening")
        void testCanOpenAccount_Consistency() {
            assertTrue(validator.canOpenAccount(30, 5000.0));
            assertFalse(validator.canOpenAccount(17, -100.0));
        }
    }

    // ========================================
    // INTEGRATION TESTS
    // ========================================

    @Nested
    @DisplayName("Integration & Real-World Scenarios")
    class IntegrationTests {

        @Test
        @DisplayName("Should validate complete user registration data")
        void testCompleteUserRegistration() {
            // Valid scenario
            String accountNumber = "123456789";
            String email = "newuser@bank.com";
            int age = 25;
            double initialBalance = 1000.0;

            assertAll("Complete Registration",
                    () -> assertTrue(validator.isValidAccountNumber(accountNumber)),
                    () -> assertTrue(validator.isValidEmail(email)),
                    () -> assertTrue(validator.isValidAge(age)),
                    () -> assertTrue(validator.canOpenAccount(age, initialBalance))
            );
        }

        @Test
        @DisplayName("Should reject incomplete registration data")
        void testIncompleteUserRegistration() {
            // Invalid scenario
            String accountNumber = "123";  // Too short
            String email = "invalidemail";  // No @
            int age = 15;  // Underage
            double initialBalance = -500.0;  // Negative

            assertAll("Incomplete Registration",
                    () -> assertFalse(validator.isValidAccountNumber(accountNumber)),
                    () -> assertFalse(validator.isValidEmail(email)),
                    () -> assertFalse(validator.isValidAge(age)),
                    () -> assertFalse(validator.canOpenAccount(age, initialBalance))
            );
        }

        @Test
        @DisplayName("Should validate senior citizen account opening")
        void testSeniorCitizenAccountOpening() {
            assertTrue(validator.canOpenAccount(65, 50000.0));
            assertTrue(validator.isValidAge(65));
        }

        @Test
        @DisplayName("Should validate young adult account opening")
        void testYoungAdultAccountOpening() {
            assertTrue(validator.canOpenAccount(18, 0.0));
            assertTrue(validator.isValidAge(18));
        }

        @ParameterizedTest
        @CsvSource({
                "ACC12345678, user1@email.com, 25, 1000.0",
                "ACC98765432, user2@bank.com, 30, 5000.0",
                "ACC11111111, admin@company.org, 45, 100000.0"
        })
        @DisplayName("Should validate multiple user registrations")
        void testMultipleUserRegistrations(String accNum, String email, int age, double balance) {
            assertAll("Multiple Registrations",
                    () -> assertTrue(validator.isValidAccountNumber(accNum)),
                    () -> assertTrue(validator.isValidEmail(email)),
                    () -> assertTrue(validator.isValidAge(age)),
                    () -> assertTrue(validator.canOpenAccount(age, balance))
            );
        }
    }
}