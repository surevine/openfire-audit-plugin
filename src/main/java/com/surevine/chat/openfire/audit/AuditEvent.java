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
 * An enumeration of Openfire system events that are audited.
 */
public enum AuditEvent {

    /**
     * A message sent to a single recipient.
     */
    CHAT_MSG,

    /**
     * A message sent to a group of recipients.
     */
    GROUP_CHAT_MSG,

    /**
     * Joining an existing or new group chat room.
     */
    JOIN_ROOM,

    /**
     * Leaving a group chat room.
     */
    LEAVE_ROOM,

    /**
     * Invitation to join a group chat.
     */
    INVITATION,
    
    /**
     * Superuser succesfully logged in
     */
    SUPERUSER_LOGIN,
    
    /**
     * Superuser failed to log in
     */
    SUPERUSER_LOGIN_FAIL;

}
