package repository.impl;

import lombok.RequiredArgsConstructor;
import model.Audit;
import repository.AuditRepository;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The implementation of the AuditRepository interface.
 */
@RequiredArgsConstructor
public class AuditRepositoryImpl implements AuditRepository {
    private final ConnectionManager connectionManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAudit(Audit audit) {
        String sql = "INSERT INTO ylab_hw.audit (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, audit.getUserId());
            preparedStatement.setString(2, audit.getAction());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(audit.getTimestamp()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while saving audit: " + e.getMessage());
            e.printStackTrace();
        }
    }

}