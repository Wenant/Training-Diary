package model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents an audit log.
 */
@Getter
@Setter
public class Audit {

    /**
     * The id of the audit log.
     */
    private Long id;

    /**
     * The timestamp of the audit log.
     */
    private LocalDateTime timestamp;

    /**
     * The userId of the user performing the action.
     */
    private Long userId;

    /**
     * The action performed by the user.
     */
    private String action;

    /**
     * Constructs an Audit object with the current timestamp.
     *
     * @param userId The username of the user performing the action.
     * @param action The action performed by the user.
     */
    public Audit(Long userId, String action) {
        this.timestamp = LocalDateTime.now();
        this.userId = userId;
        this.action = action;
    }

    /**
     * Returns a string representation of the audit log entry.
     *
     * @return A string representation of the audit log entry.
     */
    @Override
    public String toString() {
        return "AuditLog{" +
                "Time: " + timestamp +
                ", User ID: '" + userId + '\'' +
                ", Action: '" + action + '\'' +
                '}' + "\n";
    }
}