package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutTypeDTO {

    /**
     * The id of the workout type.
     */
    private Long id;

    /**
     * The type of the workout.
     */
    private String type;
}
