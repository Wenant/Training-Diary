package repository;

import model.Workout;
import model.WorkoutType;

import java.util.List;

/**
 * Interface for managing workout data.
 */
public interface WorkoutRepository {

    /**
     * Adds a new workout to the repository.
     *
     * @param workout The workout to be added.
     */
    void addWorkout(Workout workout);

    /**
     * Retrieves all workouts for a specific user.
     *
     * @param userId The username of the user.
     * @return A list of workouts for the specified user.
     */
    List<Workout> getAllUserWorkouts(Long userId);

    /**
     * Retrieves all distinct workout types.
     *
     * @return A list of all distinct workout types.
     */
    List<WorkoutType> getAllWorkoutTypes();

    /**
     * Adds a new workout type to the repository.
     *
     * @param type The new workout type to be added.
     */
    void addNewWorkoutType(String type);

    /**
     * Retrieves a specific workout for a user by index.
     *
     * @param userId    The username of the user.
     * @param workoutId The index of the workout.
     * @return The workout at the specified index for the specified user.
     */
    Workout getUserWorkoutByWorkoutId(Long userId, Long workoutId);

    /**
     * Updates an existing workout in the repository.
     *
     * @param workout The updated workout.
     */
    void editWorkout(Workout workout);

    /**
     * Deletes a workout from the repository.
     *
     * @param workoutId The index of the workout to be deleted.
     */
    void deleteWorkout(Long workoutId);
}