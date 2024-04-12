package repository.impl;


import dto.UserDTO;
import lombok.NoArgsConstructor;
import mapper.UserMapper;
import model.User;
import repository.UserRepository;

import java.util.*;

/**
 * Implementation of WorkoutRepository.
 */
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final Map<String, User> users = new HashMap<>();


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
    public List<UserDTO> getAllUsers() {
        return UserMapper.INSTANCE.userListToUserDtoList(new ArrayList<>(users.values()));
    }
}