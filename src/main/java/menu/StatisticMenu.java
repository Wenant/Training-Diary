package menu;

import in.UserInputReader;
import lombok.RequiredArgsConstructor;
import service.WorkoutStatistics;

import java.sql.Date;

@RequiredArgsConstructor
public class StatisticMenu {

    private final WorkoutStatistics statistics;

    private final UserInputReader reader;


    public void showMenu(Long userId) {

        while (true) {
            System.out.println("\nStatistics Menu:");
            System.out.println("1. View calories statistics");
            System.out.println("2. View duration statistics");
            System.out.println("3. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = reader.readInt();
            switch (choice) {
                case 1 -> viewCaloriesStatistics(userId);
                case 2 -> viewDurationStatistics(userId);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewCaloriesStatistics(Long userId) {
        System.out.println("Calories Statistics:");
        System.out.println("1. Total calories burned for all time");
        System.out.println("2. Total calories burned between dates");
        System.out.println("3. Exit");

        System.out.print("\nEnter your choice: ");

        int choice = reader.readInt();
        switch (choice) {
            case 1 -> printTotalStatistics(userId, false, null, null, "calories");
            case 2 -> printDateRangeStatistics(userId, "calories");
            case 3 -> {
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                viewCaloriesStatistics(userId);
            }
        }
    }

    private void viewDurationStatistics(Long userId) {
        System.out.println("Duration Statistics:");
        System.out.println("1. Total workout duration for all time");
        System.out.println("2. Total workout duration between dates");
        System.out.println("3. Exit");

        System.out.print("\nEnter your choice: ");

        int choice = reader.readInt();
        switch (choice) {
            case 1 -> printTotalStatistics(userId, false, null, null, "duration");
            case 2 -> printDateRangeStatistics(userId, "duration");
            case 3 -> {
            }
            default -> {
                System.out.println("Invalid choice. Please try again.");
                viewDurationStatistics(userId);
            }
        }
    }

    private void printTotalStatistics(Long userId, boolean filterByDate, Date start, Date end, String columnName) {
        int total = statistics.getTotal(userId, filterByDate, start, end, columnName);
        String columnLabel = columnName.equals("calories") ? "calories" : "workout duration";
        System.out.println("Total " + columnLabel + " for all time: " + total);
    }

    private void printDateRangeStatistics(Long userId, String columnName) {
        System.out.println("Enter start date:");
        Date start = reader.readDate();
        System.out.println("Enter end date:");
        Date end = reader.readDate();
        int total = statistics.getTotal(userId, true, start, end, columnName);
        String columnLabel = columnName.equals("calories") ? "calories" : "workout duration";
        System.out.println("Total " + columnLabel + " for all time: " + total);
    }


}