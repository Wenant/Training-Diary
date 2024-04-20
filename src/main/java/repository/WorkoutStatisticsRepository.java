package repository;

import java.sql.Date;

public interface WorkoutStatisticsRepository {

    //todo
//    int getTotalWorkoutsBetweenDates(Long userId, Date start, Date end);
//    int getTotalDurationBetweenDates(Long userId, Date start, Date end);
    int getTotalCaloriesBetweenDates(Long userId, Date start, Date end);


}
