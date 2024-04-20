package liquibase;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for running Liquibase migrations.
 */
public class LiquibaseMigration {
    private static final ConnectionManager connectionManager = new ConnectionManager();

    /**
     * Runs the Liquibase migrations.
     */
    public static void runMigrations() {
        try {
            Connection connection = connectionManager.getConnection();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migrations completed successfully!");
            connection.close();
        } catch (SQLException | LiquibaseException e) {
            System.out.println("SQLException in migrations." + e.getMessage());
        }
    }
}
