package repository;

import dto.UserDTO;
import model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing user data.
 */
public interface UserRepository {

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     */
    void registerUser(User user);

    /**
     * Checks if a username already exists in the repository.
     *
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    boolean isUsernameExists(String username);

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return An Optional containing the authenticated user if successful, otherwise empty.
     */
    Optional<User> authenticateUser(String username, String password);

    /**
     * Retrieves a list of all users in the repository.
     *
     * @return A dto list of all users.
     */
    List<UserDTO> getAllUsers();
}
