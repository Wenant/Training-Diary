import dto.UserDTO;
import dto.WorkoutDTO;
import model.Audit;
import model.User;
import model.Workout;
import repository.UserRepository;
import repository.WorkoutRepository;
import repository.impl.UserRepositoryImpl;
import repository.impl.WorkoutRepositoryImpl;
import service.UserService;
import service.WorkoutService;
import service.WorkoutStatistics;
import service.impl.UserServiceImpl;
import service.impl.WorkoutServiceImpl;
import service.impl.WorkoutStatisticsImpl;
import util.ConnectionManager;
import util.UserRoles;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConnectionManager connection = new ConnectionManager();
    private static final UserRepository userRepository = new UserRepositoryImpl(connection);
    private static final UserService userService = new UserServiceImpl(userRepository);
    private static final WorkoutRepository workoutRepository = new WorkoutRepositoryImpl();
    private static final WorkoutService workoutService = new WorkoutServiceImpl(workoutRepository);
    private static final WorkoutStatistics statistics = new WorkoutStatisticsImpl();
    private static final List<Audit> audit = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
//        ConnectionManager connectionManager = new ConnectionManager();
//        Connection connection = connectionManager.getConnection();
        if (connection != null) {
            System.out.println("Connection successful!");

        }

//        try {
//            Connection connection = connectionManager.getConnection();
//            if (connection != null) {
//                System.out.println("Connection successful!");
//                //
//            } else {
//                System.out.println("Failed to establish connection.");
//            }
//        } catch (SQLException e) {
//            System.err.println("Connection error: " + e.getMessage());
//        }


        initializeData();

        while (true) {
            showMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println(audit);
                    System.out.println("Exiting program...");

                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n Welcome to Training Diary!");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void login() {
        System.out.println("Login");
        System.out.print("Enter your username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter your password: ");
        String loginPassword = scanner.nextLine();

        Optional<User> authenticatedUserOptional = userService.authenticateUser(loginUsername, loginPassword);
        if (authenticatedUserOptional.isPresent()) {
            User authenticatedUser = authenticatedUserOptional.get();
            var username = authenticatedUser.getUsername();
            audit.add(new Audit(username, "Login"));
            System.out.println("Login successful");
            if (authenticatedUser.getRole() == UserRoles.ADMIN) {
                // меню для администратора
                showAdminMenu(authenticatedUser);
            } else if (authenticatedUser.getRole() == UserRoles.USER) {
                // меню для пользователя
                showUserMenu(authenticatedUser);
            }
        } else {
            System.out.println("Login failed");
        }
    }

    private static void register() {
        System.out.println("Registration");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        UserDTO user = new UserDTO(username, password);
        userService.registerUser(user);
    }

    private static void showAdminMenu(User authenticatedUser) {

        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. View users");
            System.out.println("2. View user workouts");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    var list = userService.getAllUsers();
                    for (UserDTO user : list) {
                        System.out.println(user.toString());
                    }
                    break;
                case 2:
                    System.out.print("Enter your username: ");
                    var username = scanner.nextLine();
                    var userWorkouts = workoutService.getAllUserWorkouts(username);
                    if (userWorkouts != null && !userWorkouts.isEmpty()) {
                        for (Workout workout : userWorkouts) {
                            System.out.println(workout.toString());
                        }
                    } else {
                        System.out.println("Have no previous workouts");
                    }
                    break;
                case 3:
                    var usernameForAudit = authenticatedUser.getUsername();
                    audit.add(new Audit(usernameForAudit, "Logout"));
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void showUserMenu(User authenticatedUser) {
        var username = authenticatedUser.getUsername();

        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View previous workouts");
            System.out.println("2. Add new workout");
            System.out.println("3. Edit workout");
            System.out.println("4. Delete workout");
            System.out.println("5. View workout statistics");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showUserWorkouts(username);
                    break;
                case 2:
                    addNewWorkout(username);
                    break;
                case 3:
                    editWorkout(username);

                    break;
                case 4:
                    deleteWorkout(username);
                    break;
                case 5:
                    // для просмотра статистики тренировок
                    showStatistics(username);
                    break;
                case 6:
                    var usernameForAudit = authenticatedUser.getUsername();
                    audit.add(new Audit(usernameForAudit, "Logout"));
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void showStatistics(String username) {
        var list = workoutService.getAllUserWorkouts(username);
        if (list != null && !list.isEmpty()) {
            var totalCaloriesBurned = statistics.calculateTotalCaloriesBurned(list);
            System.out.println("On your workouts, you burned " + totalCaloriesBurned + " calories");
        } else {
            System.out.println("Have no previous workouts");
        }
    }

    private static void showUserWorkouts(String username) {
        var list = workoutService.getAllUserWorkouts(username);
        if (list != null && !list.isEmpty()) {
            for (Workout workout : list) {
                System.out.println(workout.toString());
            }
        } else {
            System.out.println("Have no previous workouts");
        }
    }

    private static void addNewWorkout(String username) {

        Date date = setDate();
        String type = setWorkoutType();

        System.out.println("Enter workout duration (in minutes):");
        int duration = scanner.nextInt();

        System.out.println("Enter calories burned:");
        int calories = scanner.nextInt();

        Map<String, Object> additionalParams = new HashMap<>();
        boolean continueAddingParams = true;
        while (continueAddingParams) {
            System.out.println("Would you like to add an additional parameter?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter parameter name:");
                    String key = scanner.nextLine();
                    System.out.println("Enter parameter value:");
                    String value = scanner.nextLine();
                    additionalParams.put(key, value);
                    break;
                case 2:
                    continueAddingParams = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter either 1 or 2.");
                    break;
            }
        }

        WorkoutDTO workout = new WorkoutDTO(username, date, type, duration, calories, additionalParams);
        workoutService.addWorkout(workout);
        audit.add(new Audit(username, "Add new workout"));
    }

    private static String workoutTypeChoice() {
        var types = workoutService.getAllWorkoutTypes();
        System.out.println("Choose an option:");
        for (int i = 0; i < types.size(); i++) {
            System.out.println((i + 1) + ". " + types.get(i));
        }

        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= types.size()) {
            return types.get(choice - 1);
        } else {
            System.out.println("Invalid choice. Please choose a number between 1 and " + types.size());
            return workoutTypeChoice();
        }
    }


    private static void addNewWorkoutType(String type) {
        workoutService.addNewWorkoutTyp(type);
    }

    private static String setWorkoutType() {
        System.out.println("Pick workout type:");
        System.out.println("1. Choose from the list");
        System.out.println("2. Add your own");

        int typeChoice = scanner.nextInt();
        scanner.nextLine();

        switch (typeChoice) {
            case 1:
                return workoutTypeChoice();
            case 2:
                System.out.println("Enter your own type:");
                String customType = scanner.nextLine();
                addNewWorkoutType(customType);
                return customType;
            default:
                System.out.println("Invalid choice. Please enter either 1 or 2.");
                return setWorkoutType();
        }
    }

    private static Date setDate() {
        System.out.println("Enter date (yyyy-MM-dd):");
        String dateString = scanner.nextLine();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in yyyy-MM-dd format.");
            return setDate();
        }
        return date;
    }

    private static int choiceWorkout(String username) {
        var workouts = workoutService.getAllUserWorkouts(username);
        System.out.println("Choose workout:");
        for (int i = 0; i < workouts.size(); i++) {
            System.out.println((i + 1) + ". " + workouts.get(i).toStringShort());
        }

        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= workouts.size()) {
            return choice - 1;

        } else {
            System.out.println("Invalid choice. Please choose a number between 1 and " + workouts.size());
            return choiceWorkout(username);
        }
    }

    private static void editWorkout(String username) {
        var indexForEdit = choiceWorkout(username);
        var workoutDTO = workoutService.getUserWorkoutByIndex(username, indexForEdit);
        var editedWorkoutDTO = editWorkoutDTO(workoutDTO);
        workoutService.editWorkout(editedWorkoutDTO, indexForEdit);
        audit.add(new Audit(username, "Edit workout"));
    }

    private static void deleteWorkout(String username) {
        var indexForDelete = choiceWorkout(username);
        workoutService.deleteWorkoutByIndex(username, indexForDelete);
        audit.add(new Audit(username, "Delete workout"));
    }

    private static WorkoutDTO editWorkoutDTO(WorkoutDTO workout) {

        System.out.println("Editing workout fields:");
        System.out.println("Current type: " + workout.getType());
        System.out.println("1. Change");
        System.out.println("2. Next");

        int choiceType = scanner.nextInt();
        scanner.nextLine();
        if (choiceType == 1) {
            workout.setType(setWorkoutType());
        }

        System.out.println("Current date: " + workout.getDate());
        System.out.println("1. Change");
        System.out.println("2. Next");
        int choiceDate = scanner.nextInt();
        scanner.nextLine();
        if (choiceDate == 1) {
            workout.setDate(setDate());
        }

        System.out.println("Current duration: " + workout.getDuration());
        System.out.println("1. Change");
        System.out.println("2. Next");
        int choiceDuration = scanner.nextInt();
        scanner.nextLine();
        if (choiceDuration == 1) {
            System.out.println("Enter workout duration (in minutes):");
            int duration = scanner.nextInt();
            workout.setDuration(duration);
        }

        System.out.println("Current calories burned: " + workout.getCalories());
        System.out.println("1. Change");
        System.out.println("2. Next");
        int choiceCalories = scanner.nextInt();
        scanner.nextLine();
        if (choiceCalories == 1) {
            System.out.println("Enter calories burned:");
            int calories = scanner.nextInt();
            workout.setDuration(calories);
        }

        var additionalParams = workout.getAdditionalParams();
        for (Map.Entry<String, Object> entry : additionalParams.entrySet()) {
            System.out.println("Current additional Parameter: " + entry.getKey() + ": " + entry.getValue());
            System.out.println("1. Change");
            System.out.println("2. Next");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter new parameter name:");
                String newKey = scanner.nextLine();
                System.out.println("Enter new parameter value:");
                String newValue = scanner.nextLine();

                additionalParams.put(newKey, newValue);
            }
        }
        return workout;
    }

    private static void initializeData() {
//        User admin = new User("admin", "admin", UserRoles.ADMIN);
//        User user = new User("q", "q", UserRoles.USER);
//        userRepository.registerUser(admin);
//        userRepository.registerUser(user);
//        workoutRepository.addNewWorkoutType("running");
//        workoutRepository.addNewWorkoutType("cycling");
//        workoutRepository.addNewWorkoutType("swimming");
//        workoutRepository.addNewWorkoutType("weightlifting");
    }

}