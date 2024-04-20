package service;

import java.sql.Date;

/**
 * Interface for calculating workout statistics.
 */
public interface WorkoutStatistics {


    /**
     * Calculates the total value of a specific column in the workouts table for a given user within a specified date range.
     * If filterByDate is true, the calculation is performed within the specified date range.
     * If filterByDate is false, the calculation is performed for all time.
     *
     * @param userId       the ID of the user for whom to calculate the total value.
     * @param filterByDate a boolean value indicating whether to filter the calculation by date range.
     * @param start        the start date of the date range (inclusive). Ignored if filterByDate is false.
     * @param end          the end date of the date range (inclusive). Ignored if filterByDate is false.
     * @param columnName   the name of the column for which to calculate the total value.
     * @return the total value of the specified column for the given user within the specified date range,
     * or for all time if filterByDate is false.
     */
    int getTotal(Long userId, boolean filterByDate, Date start, Date end, String columnName);
}