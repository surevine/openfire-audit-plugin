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

/**
 * This exception defines errors that occur whilst auditing Openfire system
 * events.
 */
public class AuditException extends RuntimeException {

    /**
     * Serializable class ID.
     */
    private static final long serialVersionUID = 6490488382106822510L;

    /**
     * Constructs an <code>AuditException</code> with an error message and a
     * cause.
     *
     * @param message
     *            The error message.
     * @param cause
     *            The cause of the error.
     */
    public AuditException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs an <code>AuditException</code> with an error message.
     *
     * @param message
     *            The error message.
     */
    public AuditException(final String message) {
        super(message);
    }

}
