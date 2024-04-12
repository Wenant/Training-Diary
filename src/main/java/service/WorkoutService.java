package service;

import dto.WorkoutDTO;
import model.Workout;

import java.util.List;

/**
 * Interface for providing workout services.
 */
public interface WorkoutService {

    /**
     * Adds a new workout.
     *
     * @param workoutDTO The DTO representing the new workout to add.
     */
    void addWorkout(WorkoutDTO workoutDTO);

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
     * Adds a new workout type.
     *
     * @param type The new workout type to add.
     */
    void addNewWorkoutTyp(String type);

    /**
     * Retrieves a specific user's workout by index.
     *
     * @param username     The username of the user.
     * @param elementIndex The index of the workout.
     * @return The workout at the specified index for the specified user.
     */
    WorkoutDTO getUserWorkoutByIndex(String username, int elementIndex);

    /**
     * Updates an existing workout.
     *
     * @param editedWorkoutDTO The DTO representing the edited workout.
     * @param elementIndex     The index of the workout to update.
     */
    void editWorkout(WorkoutDTO editedWorkoutDTO, int elementIndex);

    /**
     * Deletes a workout.
     *
     * @param username       The username of the user.
     * @param indexForDelete The index of the workout to delete.
     */
    void deleteWorkoutByIndex(String username, int indexForDelete);
}
