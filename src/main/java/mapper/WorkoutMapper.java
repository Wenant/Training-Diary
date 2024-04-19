package mapper;

import dto.WorkoutDTO;
import dto.WorkoutTypeDTO;
import model.Workout;
import model.WorkoutType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    /**
     * Converts a list of Workout objects to a list of WorkoutDTO objects.
     *
     * @param workouts The list of Workout objects to convert.
     * @return The list of converted WorkoutDTO objects.
     */
    List<WorkoutDTO> workoutListToWorkoutDTOList(List<Workout> workouts);

    WorkoutTypeDTO workoutTypeToWorkoutTypeDTO(WorkoutType workoutType);

    /**
     * Converts a list of WorkoutType objects to a list of WorkoutTypeDTO objects.
     *
     * @param workoutTypes The list of WorkoutType objects to convert.
     * @return The list of converted WorkoutTypeDTO objects.
     */
    List<WorkoutTypeDTO> workoutTypesToWorkoutTypeDTOList(List<WorkoutType> workoutTypes);
}