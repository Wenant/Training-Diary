package menu;

import dto.WorkoutDTO;
import dto.WorkoutTypeDTO;
import in.UserInputReader;
import lombok.AllArgsConstructor;
import model.Audit;
import model.User;
import repository.AuditRepository;
import service.WorkoutService;
import service.WorkoutStatistics;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The user menu of the application.
 */
@AllArgsConstructor
public class UserMenu {
    private final WorkoutStatistics statistics;
    private final WorkoutService workoutService;
    private final List<Audit> audit;
    private final UserInputReader reader;
    private final AuditRepository auditRepository;

    private StatisticMenu statisticMenu;


    /**
     * Shows the user menu.
     *
     * @param authenticatedUser The authenticated user.
     */
    public void showMenu(User authenticatedUser) {
        var userId = authenticatedUser.getId();
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View previous workouts");
            System.out.println("2. Add new workout");
            System.out.println("3. Edit workout");
            System.out.println("4. Delete workout");
            System.out.println("5. View workout statistics");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = reader.readInt();

            switch (choice) {
                case 1 -> viewPreviousWorkouts(userId);
                case 2 -> addNewWorkout(authenticatedUser);
                case 3 -> editWorkout(userId);
                case 4 -> deleteWorkout(userId);
                case 5 -> statisticMenu.showMenu(userId);//viewStatistics(userId);
                case 6 -> {
                    auditRepository.addAudit(new Audit(userId, "Logout"));
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }

    }

    private void viewPreviousWorkouts(Long userId) {
        var list = workoutService.getAllUserWorkouts(userId);
        if (list != null && !list.isEmpty()) {
            for (WorkoutDTO workout : list) {
                System.out.println(workout.toString());
            }
        } else {
            System.out.println("Have no previous workouts");
        }
        auditRepository.addAudit(new Audit(userId, "View previous workouts"));
    }

    private void addNewWorkout(User user) {
        Long userId = user.getId();

        Date date = reader.readDate();
        Long type = setWorkoutType();

        System.out.println("Enter workout duration (in minutes):");
        int duration = reader.readInt();

        System.out.println("Enter calories burned:");
        int calories = reader.readInt();

        Map<String, Object> additionalParams = new HashMap<>();
        boolean continueAddingParams = true;
        while (continueAddingParams) {
            System.out.println("Would you like to add an additional parameter?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int choice = reader.readInt();

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter parameter name:");
                    String key = reader.readString();
                    System.out.println("Enter parameter value:");
                    String value = reader.readString();
                    additionalParams.put(key, value);
                }
                case 2 -> continueAddingParams = false;
                default -> System.out.println("Invalid choice. Please enter either 1 or 2.");

            }
        }

        WorkoutDTO workout = WorkoutDTO.builder()
                .userId(userId)
                .date(date)
                .type(type)
                .duration(duration)
                .calories(calories)
                .additionalParams(additionalParams)
                .build();
        workoutService.addWorkout(workout);
        auditRepository.addAudit(new Audit(userId, "Add new workout"));
    }


    private Long setWorkoutType() {
        System.out.println("Pick workout type:");
        System.out.println("1. Choose from the list");
        System.out.println("2. Add your own");
        int typeChoice = reader.readInt();

        switch (typeChoice) {
            case 1:
                return workoutTypeChoice();
            case 2:
                System.out.println("Enter your own type:");
                String customType = reader.readString();
                var typeId = addNewWorkoutType(customType);
                return typeId;
            default:
                System.out.println("Invalid choice. Please enter either 1 or 2.");
                return setWorkoutType();
        }
    }

    private Long workoutTypeChoice() {
        System.out.println("Enter workout type:");
        List<WorkoutTypeDTO> list = workoutService.getAllWorkoutTypes();
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getType());
        }
        int typeChoice = reader.readInt();
        return list.get(typeChoice - 1).getId();
    }

    private Long addNewWorkoutType(String type) {
        return workoutService.addNewWorkoutType(type);

    }

    private void editWorkout(Long userId) {
        var workoutDTO = selectWorkout(userId);
        var editedWorkoutDTO = editWorkoutDTO(workoutDTO);
        workoutService.editWorkout(editedWorkoutDTO);
        auditRepository.addAudit(new Audit(userId, "Edit workout"));
    }

    private void deleteWorkout(Long userId) {
        var workout = selectWorkout(userId);
        var workoutId = workout.getId();
        workoutService.deleteWorkout(workoutId);
        auditRepository.addAudit(new Audit(userId, "Delete workout"));
    }

    private WorkoutDTO editWorkoutDTO(WorkoutDTO workout) {

        System.out.println("Editing workout fields:");
        System.out.println("Current type: " + workout.getType());
        System.out.println("1. Change");
        System.out.println("2. Next");

        int choiceType = reader.readInt();
        if (choiceType == 1) {
            workout.setType(setWorkoutType());
        }

        System.out.println("Current date: " + workout.getDate());
        System.out.println("1. Change");
        System.out.println("2. Next");
        int choiceDate = reader.readInt();
        if (choiceDate == 1) {
            workout.setDate(reader.readDate());
        }

        System.out.println("Current duration: " + workout.getDuration());
        System.out.println("1. Change");
        System.out.println("2. Next");
        int choiceDuration = reader.readInt();
        if (choiceDuration == 1) {
            System.out.println("Enter workout duration (in minutes):");
            int duration = reader.readInt();
            workout.setDuration(duration);
        }

        System.out.println("Current calories burned: " + workout.getCalories());
        System.out.println("1. Change");
        System.out.println("2. Next");
        int choiceCalories = reader.readInt();
        if (choiceCalories == 1) {
            System.out.println("Enter calories burned:");
            int calories = reader.readInt();
            workout.setDuration(calories);
        }

        Map<String, Object> newAdditionalParams = new HashMap<>();
        var additionalParams = workout.getAdditionalParams();
        for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
            System.out.println("Current additional Parameter: " + entry.getKey() + ": " + entry.getValue());
            System.out.println("1. Change");
            System.out.println("2. Next");

            int choice = reader.readInt();

            if (choice == 1) {
                System.out.println("Enter new parameter name:");
                String newKey = reader.readString();
                System.out.println("Enter new parameter value:");
                String newValue = reader.readString();

                newAdditionalParams.put(newKey, newValue);
            }
            if (choice == 2) {
                newAdditionalParams.put(entry.getKey(), entry.getValue());
            }
        }
        workout.setAdditionalParams(newAdditionalParams);

        var userId = workout.getUserId();
        auditRepository.addAudit(new Audit(userId, "Edit workout"));

        return workout;
    }

    private WorkoutDTO selectWorkout(Long userId) {
        var userWorkouts = workoutService.getAllUserWorkouts(userId);
        System.out.println("Choose a workout:");
        for (int i = 0; i < userWorkouts.size(); i++) {
            System.out.println((i + 1) + ". " + userWorkouts.get(i).toStringShort());
        }

        int choice = reader.readInt();

        if (choice >= 1 && choice <= userWorkouts.size()) {
            var selectedWorkout = userWorkouts.get(choice - 1);
            return selectedWorkout;
        } else {
            System.out.println("Invalid choice. Please choose a number between 1 and " + userWorkouts.size());
            return selectWorkout(userId);
        }
    }

}