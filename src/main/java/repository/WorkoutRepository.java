package repository;

import model.Workout;

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
     * @param username The username of the user.
     * @return A list of workouts for the specified user.
     */
    List<Workout> getAllUserWorkouts(String username);

    /**
     * Retrieves all distinct workout types.
     *
     * @return A list of all distinct workout types.
     */
    List<String> getAllWorkoutTypes();

    /**
     * Adds a new workout type to the repository.
     *
     * @param type The new workout type to be added.
     */
    void addNewWorkoutType(String type);

    /**
     * Retrieves a specific workout for a user by index.
     *
     * @param username     The username of the user.
     * @param elementIndex The index of the workout.
     * @return The workout at the specified index for the specified user.
     */
    Workout getUserWorkoutByIndex(String username, int elementIndex);

    /**
     * Updates an existing workout in the repository.
     *
     * @param workout      The updated workout.
     * @param elementIndex The index of the workout to be updated.
     */
    void editWorkout(Workout workout, int elementIndex);

    /**
     * Deletes a workout from the repository by index.
     *
     * @param username       The username of the user.
     * @param indexForDelete The index of the workout to be deleted.
     */
    void deleteWorkoutByIndex(String username, int indexForDelete);
}