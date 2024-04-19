package service;

import dto.WorkoutDTO;

import java.util.List;

/**
 * Interface for calculating workout statistics.
 */
public interface WorkoutStatistics {

    /**
     * Calculates the total calories burned from a list of workouts.
     *
     * @param workouts The list of workouts for which to calculate the total calories burned.
     * @return The total calories burned.
     */
    int calculateTotalCaloriesBurned(List<WorkoutDTO> workouts);
}