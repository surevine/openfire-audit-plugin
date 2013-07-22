/*
 * Openfire Audit Plugin
 * Copyright (C) 2010 Surevine Ltd.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see {http://www.gnu.org/licenses/}.
 */

package com.surevine.chat.openfire.audit.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.surevine.chat.openfire.audit.AuditException;
import com.surevine.chat.openfire.audit.AuditMessage;
import com.surevine.chat.openfire.audit.dao.IAuditDAO;

/**
 * Concrete implementation of an {@link IAuditService} that persists audit
 * messages to an RDBMS persistence store.
 */
public class AuditService implements IAuditService {

    /**
     * Logging instance.
     */
    private static final Log LOG = LogFactory.getLog(AuditService.class);

    /**
     * The backup audit logging mechanism that will be used to log Openfire
     * system events if the audit database goes down.
     */
    private static final Log AUDIT_BACKUP_LOG = LogFactory
            .getLog("AuditBackup");

    /**
     * The DAO that will handle all audit persistent operations.
     */
    private IAuditDAO auditDao;

    /**
     * Constructs a new <code>AuditService</code> with the audit data access
     * delegate.
     *
     * @param auditDao
     *            The audit data access delegate.
     */
    public AuditService(final IAuditDAO auditDao) {
        this.auditDao = auditDao;
    }

    /**
     * {@inheritDoc}.
     */
    public void audit(final AuditMessage auditMessage) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Auditing message: " + auditMessage);
        }
        try {
            // Persist the audit message
            auditDao.save(auditMessage);
        } catch (DataAccessException dae) {
            /*
             * An error has occurred with the database that has likely prevented
             * the audit message from being saved. We need to capture this event
             * so we have a complete audit trail.
             */
            AUDIT_BACKUP_LOG.error("Auditing Event: " + auditMessage);

            /*
             * We need to audit the fact that there has been an error with the
             * database.
             */
            LOG.error("An error occurred whilst saving an audit message", dae);

            // We can't do anything with this exception so throw it
            throw new AuditException(
                    "An unrecoverable error has occurred in the database", dae);
        }
    }

}
