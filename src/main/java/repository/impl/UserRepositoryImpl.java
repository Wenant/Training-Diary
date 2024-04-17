package repository.impl;


import dto.UserDTO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import mapper.UserMapper;
import model.User;
import repository.UserRepository;
import util.ConnectionManager;

import java.util.*;

/**
 * Implementation of WorkoutRepository.
 */
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final ConnectionManager connectionManager;


    /**
     * {@inheritDoc}
     */
    @Override
    public void registerUser(User user) {


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsernameExists(String username) {

        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> authenticateUser(String username, String password) {


        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUsers() {


        return null;
    }
}