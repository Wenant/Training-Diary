package service.impl;

import model.Workout;
import service.WorkoutStatistics;

import java.util.List;

/**
 * Implementation of WorkoutStatistics for calculating workout statistics.
 */
public class WorkoutStatisticsImpl implements WorkoutStatistics {

    /**
     * Calculates the total calories burned from a list of workouts.
     *
     * @param workouts The list of workouts for calculate the total calories burned.
     * @return The total calories burned.
     */
    @Override
    public int calculateTotalCaloriesBurned(List<Workout> workouts) {
        int totalCaloriesBurned = 0;
        for (Workout workout : workouts) {
            totalCaloriesBurned += workout.getCalories();
        }
        return totalCaloriesBurned;
    }
}
