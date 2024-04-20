package repository.impl;

import containers.TestContainer;
import liquibase.LiquibaseMigration;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ConnectionManager;
import util.UserRoles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest extends TestContainer {
    private UserRepositoryImpl userRepository;

    @BeforeEach
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword()
        );

        LiquibaseMigration.runMigrations(connectionManager);

        userRepository = new UserRepositoryImpl(connectionManager);
    }




    @Test
    @DisplayName("Registering a new user should create a new user record")
    public void shouldCreateNewUserWhenRegisteringUser() {
        User userToRegister = new User();
        userToRegister.setUsername("testLogin");
        userToRegister.setPassword("testPassword");
        userToRegister.setRole(UserRoles.USER);

        userRepository.registerUser(userToRegister);
        User savedUser = userRepository.getUserByUsername(userToRegister.getUsername()).orElse(null);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(userToRegister.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(userToRegister.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(userToRegister.getRole());
    }

    @Test
    @DisplayName("Getting user by username should return the correct user")
    void shouldReturnCorrectUserWhenGettingUserByUsername() {
        User user = userRepository.getUserByUsername("testLogin").orElse(null);
        assertThat(user.getUsername()).isEqualTo("testLogin");
        assertThat(user.getPassword()).isEqualTo("testPassword");
        assertThat(user.getRole()).isEqualTo(UserRoles.USER);
    }


}