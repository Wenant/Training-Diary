package mapper;

import dto.WorkoutDTO;
import model.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Workout and WorkoutDTO objects.
 */
@Mapper
public interface WorkoutMapper {

    /**
     * Instance of the WorkoutMapper interface.
     */
    WorkoutMapper INSTANCE = Mappers.getMapper(WorkoutMapper.class);

    /**
     * Converts a WorkoutDTO object to a Workout object.
     *
     * @param workoutDTO The WorkoutDTO object to convert.
     * @return The converted Workout object.
     */
    Workout workoutDTOToWorkout(WorkoutDTO workoutDTO);

    /**
     * Converts a Workout object to a WorkoutDTO object.
     *
     * @param workout The Workout object to convert.
     * @return The converted WorkoutDTO object.
     */
    WorkoutDTO workoutToWorkoutDTO(Workout workout);
}
