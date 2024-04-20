package service.impl;

import dto.WorkoutDTO;
import dto.WorkoutTypeDTO;
import lombok.RequiredArgsConstructor;
import mapper.WorkoutMapper;
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
    public List<WorkoutDTO> getAllUserWorkouts(Long userId) {
        var userWorkouts = workoutRepository.getAllUserWorkouts(userId);
        return WorkoutMapper.INSTANCE.workoutListToWorkoutDTOList(userWorkouts);


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<WorkoutTypeDTO> getAllWorkoutTypes() {
        var workoutTypes = workoutRepository.getAllWorkoutTypes();
        return WorkoutMapper.INSTANCE.workoutTypesToWorkoutTypeDTOList(workoutTypes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long addNewWorkoutType(String type) {
        return workoutRepository.addNewWorkoutType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WorkoutDTO getUserWorkoutByWorkoutId(Long userId, Long workoutId) {
        var workout = workoutRepository.getUserWorkoutByWorkoutId(userId, workoutId);
        return WorkoutMapper.INSTANCE.workoutToWorkoutDTO(workout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editWorkout(WorkoutDTO editedWorkoutDTO) {
        var workout = WorkoutMapper.INSTANCE.workoutDTOToWorkout(editedWorkoutDTO);
        workoutRepository.editWorkout(workout);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteWorkout(Long workoutId) {
        workoutRepository.deleteWorkout(workoutId);
    }
}