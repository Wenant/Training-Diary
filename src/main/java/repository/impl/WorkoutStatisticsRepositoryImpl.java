package repository.impl;


import lombok.RequiredArgsConstructor;
import repository.WorkoutStatisticsRepository;
import util.ConnectionManager;

import java.sql.*;

@RequiredArgsConstructor
public class WorkoutStatisticsRepositoryImpl implements WorkoutStatisticsRepository {
    private final ConnectionManager connectionManager;



    /**
     * {@inheritDoc}
     */
    public int getTotal(Long userId, boolean filterByDate, Date start, Date end, String columnName) {
        int total = 0;
        String dateFilter = filterByDate ? "AND date BETWEEN ? AND ?" : "";
        String sql = "SELECT SUM(" + columnName + ") FROM ylab_hw.workouts WHERE user_id = ? " + dateFilter;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            if (filterByDate) {
                preparedStatement.setDate(2, start);
                preparedStatement.setDate(3, end);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    total = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting total " + columnName + (filterByDate ? " between dates" : "") + ": " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }


}
