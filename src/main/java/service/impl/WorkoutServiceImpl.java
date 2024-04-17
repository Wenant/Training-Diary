package service.impl;

import dto.WorkoutDTO;
import mapper.WorkoutMapper;
import model.Workout;
import repository.WorkoutRepository;
import service.WorkoutService;

import java.util.List;

/**
 * Implementation of WorkoutService.
 */
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;

    /**
     * Constructs a new WorkoutServiceImpl.
     *
     * @param workoutRepository The WorkoutRepository.
     */
    public WorkoutServiceImpl(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }


    /**
     * Adds a new workout.
     *
     * @param workoutDTO The DTO representing the new workout to add.
     */
    @Override
    public void addWorkout(WorkoutDTO workoutDTO) {
        var workout = WorkoutMapper.INSTANCE.workoutDTOToWorkout(workoutDTO);
        workoutRepository.addWorkout(workout);

    }

    /**
     * Retrieves all workouts for a specific user.
     *
     * @param username The username of the user.
     * @return A list of workouts for the specified user.
     */
    @Override
    public List<Workout> getAllUserWorkouts(String username) {
        return workoutRepository.getAllUserWorkouts(username);
    }

    /**
     * Retrieves all distinct workout types.
     *
     * @return A list of all distinct workout types.
     */
    @Override
    public List<String> getAllWorkoutTypes() {
        return workoutRepository.getAllWorkoutTypes();
    }

    /**
     * Adds a new workout type.
     *
     * @param type The new workout type to add.
     */
    @Override
    public void addNewWorkoutTyp(String type) {
        workoutRepository.addNewWorkoutType(type);
    }

    /**
     * Retrieves a specific user's workout by index.
     *
     * @param username     The username of the user.
     * @param elementIndex The index of the workout.
     * @return The workout at the specified index for the specified user.
     */
    @Override
    public WorkoutDTO getUserWorkoutByIndex(String username, int elementIndex) {
        var workout = workoutRepository.getUserWorkoutByIndex(username, elementIndex);
        return WorkoutMapper.INSTANCE.workoutToWorkoutDTO(workout);
    }

    /**
     * Updates an existing workout.
     *
     * @param editedWorkoutDTO The DTO representing the edited workout.
     * @param elementIndex     The index of the workout to update.
     */
    @Override
    public void editWorkout(WorkoutDTO editedWorkoutDTO, int elementIndex) {
        var workout = WorkoutMapper.INSTANCE.workoutDTOToWorkout(editedWorkoutDTO);
        workoutRepository.editWorkout(workout, elementIndex);

    }

    /**
     * Deletes a workout.
     *
     * @param username       The username of the user.
     * @param indexForDelete The index of the workout to delete.
     */
    @Override
    public void deleteWorkoutByIndex(String username, int indexForDelete) {
        workoutRepository.deleteWorkoutByIndex(username, indexForDelete);
    }
}