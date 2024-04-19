package service.impl;

import dto.UserDTO;
import lombok.RequiredArgsConstructor;
import mapper.UserMapper;
import model.User;
import repository.UserRepository;
import service.UserService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of UserService.
 */
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = userRepository.getUserByUsername(username);
        if (user.isEmpty()) {
            System.out.println("User not found");
            return Optional.empty();
        }
        if (!user.get().getPassword().equals(password)) {
            System.out.println("Wrong password");
            return Optional.empty();
        }
        System.out.println("User authenticated successfully");
        return user;
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        Optional<User> user = userRepository.getUserByUsername(username);
        if (user.isEmpty()) {
            System.out.println("User not found");
            return Optional.empty();
        }
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user.get());
        return Optional.of(userDTO);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        return UserMapper.INSTANCE.userListToUserDtoList(users);
    }
}