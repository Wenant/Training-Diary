package service;

import dto.WorkoutDTO;
import dto.WorkoutTypeDTO;

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
     * @param userId The username of the user.
     * @return A list of workouts for the specified user.
     */
    List<WorkoutDTO> getAllUserWorkouts(Long userId);

    /**
     * Retrieves all distinct workout types.
     *
     * @return A list of all distinct workout types.
     */
    List<WorkoutTypeDTO> getAllWorkoutTypes();

    /**
     * Adds a new workout type.
     *
     * @param type The new workout type to add.
     */
    Long addNewWorkoutType(String type);

    /**
     * Retrieves a specific user's workout by index.
     *
     * @param userId    The username of the user.
     * @param workoutId The index of the workout.
     * @return The workout at the specified index for the specified user.
     */
    WorkoutDTO getUserWorkoutByWorkoutId(Long userId, Long workoutId);

    /**
     * Updates an existing workout.
     *
     * @param editedWorkoutDTO The DTO representing the updated workout.
     */
    void editWorkout(WorkoutDTO editedWorkoutDTO);

    /**
     * Deletes a workout.
     *
     * @param workoutId The ID of the workout to delete.
     */
    void deleteWorkout(Long workoutId);
}