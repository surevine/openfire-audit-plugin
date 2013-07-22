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

import org.junit.Test;

/**
 * Test case for the class {@link AuditException}.
 */
public class AuditExceptionTest {

    /**
     * Tests that {@link AuditException} objects are correctly constructed.
     */
    @Test
    public void testConstructorWithMessageAndCause() {
        final String message = "ERROR";
        final Throwable cause = new Throwable();
        AuditException auditException = new AuditException(message, cause);

        assertEquals("The message has not been constructed properly", message,
                auditException.getMessage());
        assertEquals("The cause has not been constructed properly", cause,
                auditException.getCause());
    }

    /**
     * Tests that {@link AuditException} objects are correctly constructed.
     */
    @Test
    public void testConstructorWithMessage() {
        final String message = "ERROR";
        AuditException auditException = new AuditException(message);

        assertEquals("The message has not been constructed properly", message,
                auditException.getMessage());
    }

}
