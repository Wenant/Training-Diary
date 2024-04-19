package menu;

import dto.UserDTO;
import in.UserInputReader;
import model.Audit;
import model.User;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AppMenu {
    private static final List<Audit> audit = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final ConnectionManager connection = new ConnectionManager();
    private static final UserRepository userRepository = new UserRepositoryImpl(connection);
    private static final UserService userService = new UserServiceImpl(userRepository);
    private static final WorkoutRepository workoutRepository = new WorkoutRepositoryImpl(connection);
    private static final WorkoutService workoutService = new WorkoutServiceImpl(workoutRepository);
    private static final WorkoutStatistics statistics = new WorkoutStatisticsImpl();
    static UserInputReader reader = new UserInputReader(scanner);
    static AdminMenu adminMenu = new AdminMenu(userService, workoutService, audit, reader);
    static UserMenu userMenu = new UserMenu(statistics, workoutService, audit, reader);


    public static void run() {
        while (true) {
            showMainMenu();
            int choice = reader.readInt();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    //System.out.println(audit);
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
        String loginUsername = reader.readString();
        System.out.print("Enter your password: ");
        String loginPassword = reader.readString();

        Optional<User> authenticatedUserOptional = userService.authenticateUser(loginUsername, loginPassword);
        if (authenticatedUserOptional.isPresent()) {
            User authenticatedUser = authenticatedUserOptional.get();
            var username = authenticatedUser.getUsername();
            audit.add(new Audit(username, "Login"));
            System.out.println("Login successful");
            if (authenticatedUser.getRole() == UserRoles.ADMIN) {
                // меню для администратора
                adminMenu.showMenu(authenticatedUser);


            } else if (authenticatedUser.getRole() == UserRoles.USER) {
                // меню для пользователя
                userMenu.showMenu(authenticatedUser);
            }
        } else {
            System.out.println("Login failed");
        }
    }

    private static void register() {
        System.out.println("Registration");
        System.out.print("Enter your username: ");
        String username = reader.readString();
        System.out.print("Enter your password: ");
        String password = reader.readString();

        UserDTO user = new UserDTO(null, username, password);
        userService.registerUser(user);
    }


}
