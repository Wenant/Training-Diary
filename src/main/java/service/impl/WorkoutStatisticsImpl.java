package service.impl;

import dto.WorkoutDTO;
import service.WorkoutStatistics;

import java.util.List;

/**
 * Implementation of WorkoutStatistics for calculating workout statistics.
 */
public class WorkoutStatisticsImpl implements WorkoutStatistics {

    /**
     * {@inheritDoc}
     */
    @Override
    public int calculateTotalCaloriesBurned(List<WorkoutDTO> workouts) {
        int totalCaloriesBurned = 0;
        for (WorkoutDTO workout : workouts) {
            totalCaloriesBurned += workout.getCalories();
        }
        return totalCaloriesBurned;
    }
}