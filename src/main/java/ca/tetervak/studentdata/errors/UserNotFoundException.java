package ca.tetervak.studentdata.errors;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User not found: " + username);
    }

    public UserNotFoundException(Integer id) {
        super("User not found: " + id);
    }
}
