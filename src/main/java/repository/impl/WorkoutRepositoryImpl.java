package repository.impl;

import lombok.NoArgsConstructor;
import model.Workout;
import repository.WorkoutRepository;

import java.util.*;

/**
 * Implementation of WorkoutRepository.
 */
@NoArgsConstructor
public class WorkoutRepositoryImpl implements WorkoutRepository {
    private final Map<String, List<Workout>> userWorkouts = new HashMap<>();
    private final List<String> workoutTypes = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addWorkout(Workout newWorkout) {
        var username = newWorkout.getUsername();
        var workoutList = userWorkouts.getOrDefault(username, new ArrayList<>());

        if (!containsWorkout(workoutList, newWorkout)) {
            workoutList.add(newWorkout);
            workoutList.sort(Comparator.comparing(Workout::getDate));
            userWorkouts.put(username, workoutList);
        } else {
            System.out.println("Workout with the same date and type exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Workout> getAllUserWorkouts(String username) {
        return userWorkouts.get(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getAllWorkoutTypes() {
        return workoutTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewWorkoutType(String type) {
        workoutTypes.add(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Workout getUserWorkoutByIndex(String username, int elementIndex) {
        var workoutList = getAllUserWorkouts(username);
        return workoutList.get(elementIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editWorkout(Workout editedWorkout, int elementIndex) {
        var username = editedWorkout.getUsername();
        var workoutList = getAllUserWorkouts(username);
        var workout = workoutList.get(elementIndex);
        workout.setDate(editedWorkout.getDate());
        workout.setType(editedWorkout.getType());
        workout.setCalories(editedWorkout.getCalories());
        workout.setDuration(editedWorkout.getDuration());
        workout.setAdditionalParams(editedWorkout.getAdditionalParams());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteWorkoutByIndex(String username, int indexForDelete) {
        var workoutList = getAllUserWorkouts(username);
        workoutList.remove(indexForDelete);
        userWorkouts.put(username, workoutList);
    }

    /**
     * Checks if a workout with the same date and type already exists in the given list of workouts.
     *
     * @param workouts   The list of workouts to check.
     * @param newWorkout The new workout to compare.
     * @return True if a workout with the same date and type exists, otherwise false.
     */
    public boolean containsWorkout(List<Workout> workouts, Workout newWorkout) {
        return workouts.stream()
                .anyMatch(workout -> workout.getDate().equals(newWorkout.getDate()) &&
                        workout.getType().equals(newWorkout.getType()));
    }
}
