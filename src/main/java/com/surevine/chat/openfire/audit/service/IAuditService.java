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

import com.surevine.chat.openfire.audit.AuditMessage;

/**
 * This interface defines the contract for auditing Openfire system events.
 */
public interface IAuditService {

    /**
     * Audits an Openfire system event with the specified message.
     *
     * @param auditMessage
     *            The audit message.
     */
    void audit(AuditMessage auditMessage);

}
