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
    public int getTotal(Long userId, boolean filterByDate, Date start, Date end, String columnName) {
        return statisticsRepository.getTotal(userId, filterByDate, start, end, columnName);
    }

}