package repository.impl;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void registerUser_ShouldAddUserToRepository() {
        User user = new User("testUser", "password", UserRoles.USER);
        userRepository.registerUser(user);

        assertTrue(userRepository.isUsernameExists(user.getUsername()));
    }


    @Test
    void authenticateUser_ShouldReturnUserWhenCredentialsAreCorrect() {
        User user = new User("testUser", "password", UserRoles.USER);
        userRepository.registerUser(user);
        Optional<User> authenticatedUser = userRepository.authenticateUser(user.getUsername(), user.getPassword());

        assertTrue(authenticatedUser.isPresent());
        assertEquals(user, authenticatedUser.get());
    }

    @Test
    void authenticateUser_ShouldReturnEmptyOptionalWhenCredentialsAreIncorrect() {
        User user = new User("testUser", "password", UserRoles.USER);
        userRepository.registerUser(user);
        Optional<User> authenticatedUser = userRepository.authenticateUser(user.getUsername(), "wrongPassword");

        assertFalse(authenticatedUser.isPresent());
    }

    @Test
    void getAllUsers_ShouldReturnAllRegisteredUsers() {
        User user1 = new User("testUser1", "password1", UserRoles.USER);
        User user2 = new User("testUser2", "password2", UserRoles.USER);
        userRepository.registerUser(user1);
        userRepository.registerUser(user2);
        List<User> allUsers = userRepository.getAllUsers();


        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }
}