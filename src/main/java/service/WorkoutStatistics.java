package service;

import java.sql.Date;

/**
 * Interface for calculating workout statistics.
 */
public interface WorkoutStatistics {


    int getTotalCaloriesBetweenDates(Long userId, Date start, Date end);
}