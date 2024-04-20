package repository.impl;


import lombok.RequiredArgsConstructor;
import model.User;
import repository.UserRepository;
import util.ConnectionManager;
import util.UserRoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        String sql = "INSERT INTO ylab_hw.users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole().toString());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error while registering new user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getUserByUsername(String username) {
        String sql = "SELECT * FROM ylab_hw.users WHERE username = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(UserRoles.valueOf(resultSet.getString("role")));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting user by username: " + e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM ylab_hw.users WHERE username = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while checking if username exists: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        String sql = "SELECT * FROM ylab_hw.users";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRole(UserRoles.valueOf(resultSet.getString("role")));
                    listOfUsers.add(user);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all users: " + e.getMessage());
            e.printStackTrace();
        }

        return listOfUsers;
    }
}