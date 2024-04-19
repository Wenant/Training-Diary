package service.impl;

import dto.WorkoutDTO;
import lombok.RequiredArgsConstructor;
import mapper.WorkoutMapper;
import model.Workout;
import repository.WorkoutRepository;
import service.WorkoutService;

import java.util.List;

/**
 * Implementation of WorkoutService.
 */
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWorkout(WorkoutDTO workoutDTO) {
        var workout = WorkoutMapper.INSTANCE.workoutDTOToWorkout(workoutDTO);
        workoutRepository.addWorkout(workout);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Workout> getAllUserWorkouts(String username) {
        return workoutRepository.getAllUserWorkouts(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllWorkoutTypes() {
        return workoutRepository.getAllWorkoutTypes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewWorkoutTyp(String type) {
        workoutRepository.addNewWorkoutType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkoutDTO getUserWorkoutByIndex(String username, int elementIndex) {
        var workout = workoutRepository.getUserWorkoutByIndex(username, elementIndex);
        return WorkoutMapper.INSTANCE.workoutToWorkoutDTO(workout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editWorkout(WorkoutDTO editedWorkoutDTO, int elementIndex) {
        var workout = WorkoutMapper.INSTANCE.workoutDTOToWorkout(editedWorkoutDTO);
        workoutRepository.editWorkout(workout, elementIndex);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteWorkoutByIndex(String username, int indexForDelete) {
        workoutRepository.deleteWorkoutByIndex(username, indexForDelete);
    }
}