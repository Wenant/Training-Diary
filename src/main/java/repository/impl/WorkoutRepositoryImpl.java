package repository.impl;

import lombok.RequiredArgsConstructor;
import model.Workout;
import model.WorkoutType;
import repository.WorkoutRepository;
import util.ConnectionManager;
import util.MapToJsonConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of WorkoutRepository.
 */
@RequiredArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutRepository {
    private final ConnectionManager connectionManager;
    private final List<WorkoutType> workoutTypes = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWorkout(Workout newWorkout) {
        String sql = "INSERT INTO ylab_hw.workouts (user_id, date, type, duration, calories, additional_params) VALUES (?, ?, ?, ?, ?, ?::jsonb)";
        String json = MapToJsonConverter.mapToJson(newWorkout.getAdditionalParams());

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, newWorkout.getUserId());
            preparedStatement.setDate(2, newWorkout.getDate());
            preparedStatement.setLong(3, newWorkout.getType());
            preparedStatement.setInt(4, newWorkout.getDuration());
            preparedStatement.setInt(5, newWorkout.getCalories());
            preparedStatement.setString(6, json);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding new workout: " + e.getMessage());
            e.printStackTrace();
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Workout> getAllUserWorkouts(Long userId) {
        String sql = "SELECT * FROM ylab_hw.workouts WHERE user_id = ?";
        List<Workout> userWorkouts = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var workoutUerId = resultSet.getLong("user_id");
                    var date = resultSet.getDate("date");
                    var type = resultSet.getLong("type");
                    var duration = resultSet.getInt("duration");
                    var calories = resultSet.getInt("calories");
                    var json = resultSet.getString("additional_params");
                    var additionalParams = MapToJsonConverter.jsonToMap(json);
                    var typeName = getWorkoutTypeName(type);
                    var workout = new Workout(id, workoutUerId, date, type, typeName, duration, calories, additionalParams);
                    userWorkouts.add(workout);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all user workouts: " + e.getMessage());
            e.printStackTrace();
        }
        return userWorkouts;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkoutType> getAllWorkoutTypes() {
        String sql = "SELECT * FROM ylab_hw.workout_type";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String type = resultSet.getString("type");
                    var workoutType = new WorkoutType(id, type);
                    workoutTypes.add(workoutType);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting all workout types: " + e.getMessage());
            e.printStackTrace();
        }
        return workoutTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long addNewWorkoutType(String type) {
        String sql = "INSERT INTO ylab_hw.workout_type (type) VALUES (?)";
        Long generatedId = -1L;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, type);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = (long) generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error while adding new workout type: " + e.getMessage());
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Workout getUserWorkoutByWorkoutId(Long userId, Long workoutId) {
        String slq = "SELECT * FROM ylab_hw.workouts WHERE user_id = ? AND id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(slq)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, workoutId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    var id = resultSet.getLong("id");
                    var date = resultSet.getDate("date");
                    var type = resultSet.getLong("type");
                    var duration = resultSet.getInt("duration");
                    var calories = resultSet.getInt("calories");
                    var json = resultSet.getString("additional_params");
                    var additionalParams = MapToJsonConverter.jsonToMap(json);
                    var typeName = getWorkoutTypeName(type);
                    var workout = new Workout(id, userId, date, type, typeName, duration, calories, additionalParams);
                    return workout;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting user workout by index: " + e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editWorkout(Workout editedWorkout) {
        String sql = "UPDATE ylab_hw.workouts SET " +
                "user_id = ?, " +
                "date = ?, " +
                "type = ?, " +
                "duration = ?, " +
                "calories = ?, " +
                "additional_params = ?::jsonb " +
                "WHERE id = ?";
        String json = MapToJsonConverter.mapToJson(editedWorkout.getAdditionalParams());
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, editedWorkout.getUserId());
            preparedStatement.setDate(2, editedWorkout.getDate());
            preparedStatement.setLong(3, editedWorkout.getType());
            preparedStatement.setInt(4, editedWorkout.getDuration());
            preparedStatement.setInt(5, editedWorkout.getCalories());
            preparedStatement.setString(6, json);
            preparedStatement.setLong(7, editedWorkout.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while editing workout: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteWorkout(Long workoutId) {
        String sql = "DELETE FROM ylab_hw.workouts WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, workoutId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting workout: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * {@inheritDoc}
     */
    private String getWorkoutTypeName(Long typeId) {
        String typeName = null;
        String sql = "SELECT type FROM ylab_hw.workout_type WHERE id = ?";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, typeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    typeName = resultSet.getString("type");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while getting workout type name: " + e.getMessage());
            e.printStackTrace();
        }
        return typeName;
    }

}