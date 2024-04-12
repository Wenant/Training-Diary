package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

/**
 * Data Transfer Object (DTO) representing a workout.
 */
@AllArgsConstructor
@Getter
@Setter
public class WorkoutDTO {

    /**
     * The username of the user who performed the workout.
     */
    private String username;

    /**
     * The date when the workout was performed.
     */
    private Date date;

    /**
     * The type of workout.
     */
    private String type;

    /**
     * The duration of the workout in minutes.
     */
    private int duration;

    /**
     * The number of calories burned during the workout.
     */
    private int calories;

    /**
     * Additional parameters associated with the workout.
     */
    private Map<String, Object> additionalParams;
}
