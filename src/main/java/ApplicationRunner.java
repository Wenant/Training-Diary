import liquibase.LiquibaseMigration;
import menu.AppMenu;

/**
 * The entry point of the application.
 */
public class ApplicationRunner {

    /**
     * The main method of the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        LiquibaseMigration.runMigrations();
        AppMenu.run();


    }
}