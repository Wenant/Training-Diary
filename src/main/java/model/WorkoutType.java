package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a workout type.
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class WorkoutType {

    /**
     * The id of the workout type.
     */
    Long id;

    /**
     * The type of the workout.
     */
    String type;
}
