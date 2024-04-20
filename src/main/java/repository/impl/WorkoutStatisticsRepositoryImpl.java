package repository.impl;


import lombok.RequiredArgsConstructor;
import repository.WorkoutStatisticsRepository;
import util.ConnectionManager;

import java.sql.*;

@RequiredArgsConstructor
public class WorkoutStatisticsRepositoryImpl implements WorkoutStatisticsRepository {
    private final ConnectionManager connectionManager;


    @Override
    public int getTotalCaloriesBetweenDates(Long userId, Date start, Date end) {
        int totalCalories = 0;
        String sql = "SELECT SUM(calories) FROM ylab_hw.workouts WHERE user_id = ? AND date BETWEEN ? AND ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setDate(2, start);
            preparedStatement.setDate(3, end);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalCalories = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting total calories between dates: " + e.getMessage());
            e.printStackTrace();
        }
        return totalCalories;
    }
}
