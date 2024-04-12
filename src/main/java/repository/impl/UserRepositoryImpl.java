package repository.impl;


import model.User;
import repository.UserRepository;
import util.UserRoles;

import java.util.*;

/**
 * Implementation of WorkoutRepository.
 */
public class UserRepositoryImpl implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    /**
     * Constructs a new UserRepositoryImpl and initializes it with default users.
     */
    public UserRepositoryImpl() {
        users = new HashMap<>();
        initializeUsers();
    }

    /**
     * Initializes the repository with default users.
     */
    private void initializeUsers() {
        User admin = new User("admin", "admin", UserRoles.ADMIN);
        User user = new User("q", "q", UserRoles.USER);
        users.put(admin.getUsername(), admin);
        users.put(user.getUsername(), user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerUser(User user) {
        users.put(user.getUsername(), user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsernameExists(String username) {
        return users.containsKey(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> authenticateUser(String username, String password) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
