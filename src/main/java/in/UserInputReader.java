package in;

import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

@AllArgsConstructor
public class UserInputReader {
    private final Scanner scanner;


    /**
     * Reads a string from the user.
     *
     * @return The read string.
     */
    public String readString() {
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                break;
            } else {
                System.out.println("Input cannot be empty.");
            }
        }
        return input;
    }

    /**
     * Reads a date from the user.
     *
     * @return The read date.
     */
    public Date readDate() {
        Scanner scanner = new Scanner(System.in);
        int year, month, day;

        do {
            System.out.print("Enter the year (from 1990 to 2100):");
            year = scanner.nextInt();
        } while (year < 1990 || year > 2100);

        do {
            System.out.print("Enter the month (from 1 to 12): ");
            month = scanner.nextInt();
        } while (month < 1 || month > 12);

        do {
            System.out.print("Enter the day (not more than 31): ");
            day = scanner.nextInt();
        } while (day < 1 || day > 31);

        Date date = new Date(year - 1900, month - 1, day);

        return date;
    }

    /**
     * Reads an integer from the user.
     *
     * @return The read integer.
     */
    public int readInt() {
        Scanner scanner = new Scanner(System.in);
        int num;

        while (true) {
            try {
                num = scanner.nextInt();
                if (isValidInt(num)) {
                    break;
                } else {
                    System.out.println("Please enter a valid integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid format. Please enter an integer.");
                scanner.nextLine();
            }
        }
        return num;
    }

    /**
     * Checks if a number is valid.
     *
     * @param num The number to check.
     * @return True if the number is valid, false otherwise.
     */
    private boolean isValidInt(long num) {
        return num >= 0 && num <= Integer.MAX_VALUE;
    }
}
