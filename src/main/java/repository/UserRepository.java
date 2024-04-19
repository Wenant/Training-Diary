package repository;

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
     * Retrieves a user by username.
     *
     * @param username The username of the user to retrieve.
     * @return An Optional containing the user if found, otherwise empty.
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Checks if a username already exists in the repository.
     *
     * @param username The username to check.
     * @return True if the username exists, false otherwise.
     */
    boolean isUsernameExists(String username);


    /**
     * Retrieves a list of all users in the repository.
     *
     * @return A dto list of all users.
     */
    List<User> getAllUsers();


}