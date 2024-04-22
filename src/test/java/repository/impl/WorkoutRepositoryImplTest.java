package repository.impl;

import containers.TestContainer;
import liquibase.LiquibaseMigration;
import model.User;
import model.Workout;
import model.WorkoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.ConnectionManager;
import util.UserRoles;

import java.sql.Date;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WorkoutRepositoryImplTest extends TestContainer {
    private WorkoutRepositoryImpl workoutRepository;
    private UserRepositoryImpl userRepository;
    private User workoutUser;

    @BeforeEach
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(
                container.getJdbcUrl(),
                container.getUsername(),
                container.getPassword()
        );

        LiquibaseMigration.runMigrations(connectionManager);
        workoutRepository = new WorkoutRepositoryImpl(connectionManager);
        userRepository = new UserRepositoryImpl(connectionManager);

    }

    @Test
    @DisplayName("Adding a new workout type should ensure the new type is in the list")
    void shouldAddNewTypeInList() {
        var allWorkoutTypesBefore = workoutRepository.getAllWorkoutTypes();
        var newWorkoutTypeName = "type for testing";

        assertThat(allWorkoutTypesBefore.stream().anyMatch(workoutType -> workoutType.getType().equals(newWorkoutTypeName))).isFalse();

        workoutRepository.addNewWorkoutType(newWorkoutTypeName);
        var allWorkoutTypesAfter = workoutRepository.getAllWorkoutTypes();

        assertThat(allWorkoutTypesAfter.stream().anyMatch(workoutType -> workoutType.getType().equals(newWorkoutTypeName))).isTrue();
    }

    @Test
    @DisplayName("Adding a new workout should create a new workout record")
    void shouldCreateNewWorkoutWhenAddingWorkout() {
        User user = new User(null,"testUsername","testPassword",UserRoles.USER);

        userRepository.registerUser(user);
        workoutUser = userRepository.getUserByUsername(user.getUsername()).orElse(null);
        var userId = workoutUser.getId();

        Workout newWorkout = Workout.builder()
                .userId(userId)
                .date(Date.valueOf("2024-04-21"))
                .type(1L) // migrations adds 3 workout types by default
                .duration(60)
                .calories(300)
                .additionalParams(Collections.emptyMap())
                .build();

        workoutRepository.addWorkout(newWorkout);
        Long workoutId = 4L; // migrations adds 3 workouts by default
        Workout savedWorkout = workoutRepository.getUserWorkoutByWorkoutId(userId, workoutId);

        assertThat(savedWorkout.getId()).isNotNull();
        assertThat(savedWorkout.getUserId()).isEqualTo(newWorkout.getUserId());
        assertThat(savedWorkout.getDate()).isEqualTo(newWorkout.getDate());
        assertThat(savedWorkout.getType()).isEqualTo(newWorkout.getType());
        assertThat(savedWorkout.getDuration()).isEqualTo(newWorkout.getDuration());
        assertThat(savedWorkout.getCalories()).isEqualTo(newWorkout.getCalories());
        assertThat(savedWorkout.getAdditionalParams()).isEqualTo(newWorkout.getAdditionalParams());
    }


    @Test
    @DisplayName("Should return all workout types")
    void shouldReturnAllWorkoutTypes() {
        var allWorkoutTypes = workoutRepository.getAllWorkoutTypes();

        assertThat(allWorkoutTypes.isEmpty()).isFalse();
        for (WorkoutType workoutType : allWorkoutTypes) {
            assertThat(workoutType.getId()).isNotNull();
            assertThat(workoutType.getType()).isNotEmpty();
        }
    }

}