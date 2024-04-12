package service;

import dto.UserDTO;
import model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for providing user services.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param userDTO The DTO representing the new user to register.
     */
    void registerUser(UserDTO userDTO);

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return An Optional containing the authenticated user if successful, otherwise empty.
     */
    Optional<User> authenticateUser(String username, String password);

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    List<UserDTO> getAllUsers();
}
