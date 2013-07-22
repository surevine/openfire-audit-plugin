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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import com.surevine.chat.openfire.audit.AuditEvent;
import com.surevine.chat.openfire.audit.AuditException;
import com.surevine.chat.openfire.audit.AuditMessage;
import com.surevine.chat.openfire.audit.dao.IAuditDAO;

/**
 * Test case for the class {@link AuditService}.
 */
public class AuditServiceTest {

    /**
     * Test data for the audit message to be audited.
     */
    private AuditMessage auditMessage;

    /**
     * The mock auditing DAO.
     */
    private IAuditDAO mockAuditDao;

    /**
     * The audit service under test.
     */
    private AuditService auditService;

    /**
     * Set up the test fixtures.
     */
    @Before
    public void setUp() {
        auditMessage = new AuditMessage(AuditEvent.JOIN_ROOM, "sender",
                "receiver", new Date());
        mockAuditDao = createMock(IAuditDAO.class);
        auditService = new AuditService(mockAuditDao);
    }

    /**
     * Tear down the test fixtures.
     */
    @After
    public void tearDown() {
        auditMessage = null;
        mockAuditDao = null;
        auditService = null;
    }

    /**
     * Tests that the audit method of the {@link AuditService} class correctly
     * audits a message.
     */
    @Test
    public void testAudit() {
        mockAuditDao.save(auditMessage);
        replay(mockAuditDao);
        auditService.audit(auditMessage);
        verify(mockAuditDao);
    }

    /**
     * Tests that the audit method of the {@link AuditService} class correctly
     * handles audit errors.
     */
    @Test(expected = AuditException.class)
    public void testAuditException() {
        mockAuditDao.save(auditMessage);
        // Throw any Spring DAO exception that could occur
        expectLastCall().andThrow(new DataIntegrityViolationException("Error"));
        replay(mockAuditDao);
        auditService.audit(auditMessage);
        verify(mockAuditDao);
    }

}
