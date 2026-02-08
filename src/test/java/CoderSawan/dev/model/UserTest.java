package CoderSawan.dev.model;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Model Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    private User validUser;

    @BeforeEach
        // it will create every time new  fresh user before the every test
    void setUp() {
        validUser = new User("U001", "John Doe", "john@email.com", 30);

    }

    @AfterEach
        // destroy test user for creating new for new test
    void tearDown() {
        validUser = null;
    }

    // ================Constructor Test - Valid Cases===========

    @Test
    @Order(1)
    @DisplayName("Should create user with valid data")
    void testCreateUser_ValidData() {
        assertAll("User creation",
                () -> assertNotNull(validUser),
                () -> assertEquals("U001", validUser.getUserId()),
                () -> assertEquals("John Doe", validUser.getName()),
                () -> assertEquals("john@email.com", validUser.getEmail()),
                () -> assertEquals(30, validUser.getAge())
        );
    }

    //====Constructor Test- Invalid Cases========

    @Test
    @Order(2)
    @DisplayName("Should throw exception when userId is null")
    void testCreateUser_NullUseId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "John Doe", "john@email.com", 30);
        }); // act
        assertEquals("User ID cannot be empty", exception.getMessage()); // Assert
    }

    @Test
    @Order(3)
    @DisplayName("Should throw exception  when userId is empty")
    void testCreateUser_EmptyUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "john Doe", "john@email.com", 30);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    @DisplayName("Should throw exception for blank names")
    void testCreateUser_BlankName(String name) {   // in the place of the name we pass the argument where the value source passed
        assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", name, "john@email.com", 25);
        });
    }


    @ParameterizedTest
    @ValueSource(strings = {"notanemail", "test.com", "123345"})
    @DisplayName("Should throw exception for invalid email formats")
    void testCreateUser_InvalidEmail(String email) {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", "John", email, 25);
        });
    }


    @ParameterizedTest
    @ValueSource(ints = {17, 10, 5, 0, -1})
    @DisplayName("Should throw exception when age is below 18")
    void testCreateUser_UnderAge(int age) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", "John", "john@email.com", age);
        });

        assertEquals("User must be at least 18 years old", exception.getMessage());
    }




    //======================Business Logic===========

    @ParameterizedTest
    @CsvSource({
            "18, true",
            "25, true",
            "50, true",
            "17, false"  // Edge case - though constructor won't allow this
    })
    @DisplayName("Should correctly identify adult users")
    void testIsAdult(int age, boolean expectedResult) {
        if (age >= 18) {  // Only test valid ages
            User user = new User("U001", "Test", "test@email.com", age);
            assertEquals(expectedResult, user.isAdult());
        }
    }

    @Test
    @DisplayName("Should return true for senior citizen (age >= 60)")
    void testIsSenior_SeniorAge() {
        User senior = new User("U002", "Senior", "senior@email.com", 65);
        assertTrue(senior.isSenior());
    }

    @Test
    @DisplayName("Should return false for non-senior (age < 60)")
    void testIsSenior_NonSeniorAge() {
        assertFalse(validUser.isSenior());  // validUser is 30
    }

    @Test
    @DisplayName("Should return true for exactly 60 years old")
    void testIsSenior_ExactlySixty() {
        User sixty = new User("U003", "Sixty", "sixty@email.com", 60);
        assertTrue(sixty.isSenior());
    }



    // ========================Nested Tests for Grouping=============

    @Nested
    @DisplayName("Email Validation Tests")
    class EmailTests {

        @Test
        @DisplayName("Should accept valid email with dot")
        void testValidEmailWithDot() {
            User user = new User("U001", "Test", "test.user@email.com", 25);
            assertEquals("test.user@email.com", user.getEmail());
        }

        @Test
        @DisplayName("Should accept email with numbers")
        void testEmailWithNumbers() {
            User user = new User("U001", "Test", "user123@email.com", 25);
            assertEquals("user123@email.com", user.getEmail());
        }
    }
}