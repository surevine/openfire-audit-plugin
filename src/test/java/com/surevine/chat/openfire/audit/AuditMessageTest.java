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

package com.surevine.chat.openfire.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the class {@link AuditMessage}.
 */
public class AuditMessageTest {

    /**
     * Test data for a message sender.
     */
    private static final String SENDER = "someone@somewhere.com";

    /**
     * Test data for a message receiver.
     */
    private static final String RECEIVER = "someone.else@somewhere.com";

    /**
     * Test data for the time a message was sent.
     */
    private static final Date NOW = new Date();

    /**
     * Test data for a message content.
     */
    private static final String CONTENT = "Hello Someone";

    /**
     * Test data for a mock message security label.
     */
    private static final String SECURITY_LABEL = "Security Label";

    /**
     * The system audit message under test.
     */
    private AuditMessage auditMessage;

    /**
     * Sets up the test fixtures.
     */
    @Before
    public void setUp() {
        auditMessage = new AuditMessage(AuditEvent.CHAT_MSG, SENDER, RECEIVER,
                NOW, CONTENT, SECURITY_LABEL);
    }

    /**
     * Tears down the test fixtures.
     */
    @After
    public void tearDown() {
        auditMessage = null;
    }

    /**
     * Tests that partially constructed {@link AuditMessage} objects set the
     * correct default data.
     */
    @Test
    public void testConstructor() {
        AuditMessage partialAuditMessage = new AuditMessage(
                AuditEvent.JOIN_ROOM, SENDER, RECEIVER, NOW);
        assertNull("Content is not equal to null", partialAuditMessage
                .getContent());
        assertNull("Security Label is not equal to null", partialAuditMessage
                .getSecurityLabel());
    }

    /**
     * Tests that the {@link AuditMessage} getter methods return the correct
     * data.
     */
    @Test
    public void testGetters() {
        assertEquals("Audit Event incorrectly returned", AuditEvent.CHAT_MSG,
                auditMessage.getEvent());
        assertEquals("Sender incorrectly returned", SENDER, auditMessage
                .getSender());
        assertEquals("Receiver incorrectly returned", RECEIVER, auditMessage
                .getReceiver());
        assertEquals("Event Time incorrectly returned", NOW, auditMessage
                .getEventTime());
        assertEquals("Content incorrectly returned", CONTENT, auditMessage
                .getContent());
        assertEquals("Security Label incorrectly returned", SECURITY_LABEL,
                auditMessage.getSecurityLabel());
    }

    /**
     * Tests that the {@link AuditMessage} class overrides the toString method.
     */
    @Test
    public void testToString() {
        try {
            AuditMessage.class.getDeclaredMethod("toString", new Class<?>[] {});
        } catch (NoSuchMethodException nsme) {
            fail("AuditMessage does not override toString");
        } catch (SecurityException se) {
            fail("AuditMessage does not override toString");
        }
    }

}
