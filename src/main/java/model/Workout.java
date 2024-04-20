package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Represents a workout by a user.
 */
@Getter
@Setter
@AllArgsConstructor
public class Workout {

    /**
     * The id of the workout.
     */
    private Long id;

    /**
     * The id of the user who completed the workout.
     */
    private Long userId;


    /**
     * The date when the workout was completed.
     */
    private Date date;

    /**
     * The type of workout.
     */
    private Long type;


    /**
     * The name of the workout type.
     */
    private String typeName;

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

    /**
     * Returns a string representation of the workout.
     *
     * @return A string representation of the workout.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nWorkout Details:");
        sb.append("\nDate: ").append(new SimpleDateFormat("yyyy-MM-dd").format(date));
        sb.append("\nType: ").append(typeName);
        sb.append("\nDuration: ").append(duration).append(" minutes");
        sb.append("\nCalories: ").append(calories);
        if (additionalParams != null && !additionalParams.isEmpty()) {
            sb.append("\nAdditional Parameters:");
            for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
                sb.append("\n").append(entry.getKey()).append(": ").append(entry.getValue());
            }
        }
        return sb.toString();
    }

    /**
     * Returns a short string representation of the workout, containing only date and type.
     *
     * @return A short string representation of the workout.
     */
    public String toStringShort() {
        return "Date: " + new SimpleDateFormat("yyyy-MM-dd").format(date) + ", Type: " + typeName;
    }

}