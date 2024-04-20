package service.impl;

import lombok.RequiredArgsConstructor;
import repository.WorkoutStatisticsRepository;
import service.WorkoutStatistics;

import java.sql.Date;

/**
 * Implementation of WorkoutStatistics for calculating workout statistics.
 */
@RequiredArgsConstructor
public class WorkoutStatisticsImpl implements WorkoutStatistics {
    private final WorkoutStatisticsRepository statisticsRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalCaloriesBetweenDates(Long userId, Date start, Date end) {
        return statisticsRepository.getTotalCaloriesBetweenDates(userId, start, end);
    }

}