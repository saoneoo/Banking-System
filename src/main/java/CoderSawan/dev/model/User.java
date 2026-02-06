package CoderSawan.dev.model;

public class User {
    private String userId;
    private String name;
    private String email;
    private int age;

    public User(String userId, String name, String email, int age) {
        // Validation
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (age < 18) {
            throw new IllegalArgumentException("User must be at least 18 years old");
        }
        if (age > 120) {
            throw new IllegalArgumentException("Invalid age");
        }

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.age = age;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public boolean isAdult() {
        return age >= 18;
    }

    public boolean isSenior() {
        return age >= 60;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}