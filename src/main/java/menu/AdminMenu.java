package menu;

import dto.UserDTO;
import dto.WorkoutDTO;
import in.UserInputReader;
import lombok.AllArgsConstructor;
import model.Audit;
import model.User;
import service.UserService;
import service.WorkoutService;

import java.util.List;

@AllArgsConstructor
public class AdminMenu {
    private final UserService userService;
    private final WorkoutService workoutService;
    private final List<Audit> audit;
    private final UserInputReader reader;

    public void showMenu(User authenticatedUser) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View users");
            System.out.println("2. View user workouts");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = reader.readInt();

            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    viewUserWorkouts();
                    break;
                case 3:
                    logout(authenticatedUser);

                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void viewUsers() {
        var userList = userService.getAllUsers();
        for (UserDTO user : userList) {
            System.out.println(user.toString());
        }
    }

    private void viewUserWorkouts() {
        System.out.print("Enter username: ");
        String username = reader.readString();

        var userDTO = userService.getUserByUsername(username);
        if (userDTO.isPresent()) {
            Long userId = userDTO.get().getId();
            var userWorkouts = workoutService.getAllUserWorkouts(userId);
            if (userWorkouts != null && !userWorkouts.isEmpty()) {
                for (WorkoutDTO workout : userWorkouts) {
                    System.out.println(workout.toString());
                }
            } else {
                System.out.println("No previous workouts found for this user");
            }
        } else {
            System.out.println("User not found");
        }
    }

    private void logout(User authenticatedUser) {
        var userId = authenticatedUser.getId();
        audit.add(new Audit(userId, "Logout"));
        System.out.println("Exiting...");
    }
}