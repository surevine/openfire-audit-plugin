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

package com.surevine.chat.openfire.audit.dao;

import java.sql.Timestamp;
import java.sql.Types;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.surevine.chat.openfire.audit.AuditMessage;

/**
 * Concrete implementation of an {@link IAuditDAO} that uses Spring's JDBC
 * Template to handle the persistence of {@link AuditMessage} objects to
 * persistent storage.
 */
public class JdbcAuditDAO implements IAuditDAO {

    /**
     * Logging instance.
     */
    private static final Log LOG = LogFactory.getLog(JdbcAuditDAO.class);

    /**
     * The SQL statement to persist audit messages to the database.
     */
    private static final String INSERT = "INSERT INTO chat_audit_log "
            + "(EVENT, SENDER, RECEIVER, EVENT_TIME, CONTENT, LABEL) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * The Spring JDBC delegate that will handle all the database operations.
     */
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructs an <code>JdbcAuditDAO</code> with the {@link DataSource}
     * reference that will provide connections for audit persistence operations.
     *
     * @param dataSource
     *            The data source to persist to.
     */
    public JdbcAuditDAO(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}.
     */
    public void save(final AuditMessage auditMessage) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Saving audit message: " + auditMessage);
        }
        // Values being persisted
        Object[] args = {auditMessage.getEvent().toString(),
                auditMessage.getSender(), auditMessage.getReceiver(),
                new Timestamp(auditMessage.getEventTime().getTime()),
                auditMessage.getContent(), auditMessage.getSecurityLabel() };
        // SQL types for the arguments being persisted
        int[] argTypes = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.TIMESTAMP, Types.VARCHAR, Types.VARCHAR };
        // Persist the audit message
        jdbcTemplate.update(INSERT, args, argTypes);
    }

}
