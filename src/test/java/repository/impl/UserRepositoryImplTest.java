package repository.impl;

import dto.UserDTO;
import mapper.UserMapper;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import repository.UserRepository;
import util.UserRoles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl();
    }

    @Test
    @DisplayName("Registering a user should add the user to the repository")
    void registerUser_ShouldAddUserToRepository() {
        User user = new User("testUser", "password", UserRoles.USER);
        userRepository.registerUser(user);

        assertTrue(userRepository.isUsernameExists(user.getUsername()));
    }


    @Test
    @DisplayName("Authenticating a user should return the user when credentials are correct")
    void authenticateUser_ShouldReturnUserWhenCredentialsAreCorrect() {
        User user = new User("testUser", "password", UserRoles.USER);
        userRepository.registerUser(user);
        Optional<User> authenticatedUser = userRepository.authenticateUser(user.getUsername(), user.getPassword());

        assertTrue(authenticatedUser.isPresent());
        assertEquals(user, authenticatedUser.get());
    }

    @Test
    @DisplayName("Authenticating a user should return an empty Optional when credentials are incorrect")
    void authenticateUser_ShouldReturnEmptyOptionalWhenCredentialsAreIncorrect() {
        User user = new User("testUser", "password", UserRoles.USER);
        userRepository.registerUser(user);
        Optional<User> authenticatedUser = userRepository.authenticateUser(user.getUsername(), "wrongPassword");

        assertFalse(authenticatedUser.isPresent());
    }

    @Test
    @DisplayName("getAllUsers method should return all registered users")
    void getAllUsers_ShouldReturnAllRegisteredUsers() {
        User user1 = new User("testUser1", "password1", UserRoles.USER);
        User user2 = new User("testUser2", "password2", UserRoles.USER);
        userRepository.registerUser(user1);
        userRepository.registerUser(user2);
        List<UserDTO> allUsers = userRepository.getAllUsers();
        assertEquals(2, allUsers.size());
        assertEquals("testUser1", allUsers.get(1).getUsername());
        assertEquals("testUser2", allUsers.get(0).getUsername());
    }
}