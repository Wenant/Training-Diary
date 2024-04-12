package service.impl;

import dto.UserDTO;
import mapper.UserMapper;
import model.User;
import repository.UserRepository;
import service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService.
 */
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * Constructs a new UserServiceImpl.
     *
     * @param userRepository The UserRepository.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     *
     * @param userDTO The DTO representing the new user to register.
     */
    @Override
    public void registerUser(UserDTO userDTO) {
        var username = userDTO.getUsername();
        var isUsernameAlreadyExists = userRepository.isUsernameExists(username);
        if (isUsernameAlreadyExists) {
            System.out.println("User with that name already exists");
        } else {
            User newUser = UserMapper.INSTANCE.userDTOToUser(userDTO);
            userRepository.registerUser(newUser);
            System.out.println("User registered successfully");
        }

    }

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username The username of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return An Optional containing the authenticated user if successful, otherwise empty.
     */
    @Override
    public Optional<User> authenticateUser(String username, String password) {
        return userRepository.authenticateUser(username, password);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.getAllUsers();
    }
}