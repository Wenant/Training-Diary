package repository;

import model.Audit;


public interface AuditRepository {

    /**
     * Adds an audit to the repository.
     *
     * @param audit The audit to add.
     */
    void addAudit(Audit audit);
}
