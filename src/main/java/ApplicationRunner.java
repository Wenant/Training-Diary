import liquibase.LiquibaseMigration;
import menu.AppMenu;
import util.ConnectionManager;

/**
 * The entry point of the application.
 */
public class ApplicationRunner {
    private static final ConnectionManager connectionManager = new ConnectionManager();

    /**
     * The main method of the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        LiquibaseMigration.runMigrations(connectionManager);
        AppMenu.run();

    }
}