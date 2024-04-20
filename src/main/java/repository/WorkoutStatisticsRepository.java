package repository;

import java.sql.Date;

public interface WorkoutStatisticsRepository {

    int getTotalCaloriesBetweenDates(Long userId, Date start, Date end);
}
