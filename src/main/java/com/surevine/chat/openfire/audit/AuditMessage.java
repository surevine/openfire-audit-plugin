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

import java.util.Date;

/**
 * An <code>AuditMessage</code> represents the information that is persisted for
 * an Openfire system event and that is required to be audited. Example system
 * events include sending a message, joining and leaving group chat
 * conversations.
 */
public class AuditMessage {

    /**
     * The type of event being audited.
     */
    private AuditEvent event;

    /**
     * The message sender which could be an individual or a group.
     */
    private String sender;

    /**
     * The message receiver which could be an individual or a group.
     */
    private String receiver;

    /**
     * The time that the message was handled by the server.
     */
    private Date eventTime;

    /**
     * The message content.
     */
    private String content;

    /**
     * The security label applied to the message.
     */
    private String securityLabel;

    /**
     * Constructs an <code>AuditMessage</code> with the details of an audit
     * event.
     *
     * @param event
     *            The type of event being audited.
     * @param sender
     *            The message sender which could be an individual or a group.
     * @param receiver
     *            The message receiver which could be an individual or a group.
     * @param eventTime
     *            The time that the event was handled by the server.
     */
    public AuditMessage(final AuditEvent event, final String sender,
            final String receiver, final Date eventTime) {
        this.event = event;
        this.sender = sender;
        this.receiver = receiver;
        this.eventTime = eventTime;
    }

    /**
     * Constructs an <code>AuditMessage</code> with the details of an audit
     * event.
     *
     * @param event
     *            The type of event being audited.
     * @param sender
     *            The message sender which could be an individual or a group.
     * @param receiver
     *            The message receiver which could be an individual or a group.
     * @param eventTime
     *            The time that the event was handled by the server.
     * @param content
     *            The message content.
     * @param securityLabel
     *            The message security label.
     */
    public AuditMessage(final AuditEvent event, final String sender,
            final String receiver, final Date eventTime, final String content,
            final String securityLabel) {
        this(event, sender, receiver, eventTime);
        this.content = content;
        this.securityLabel = securityLabel;
    }

    /**
     * Gets the type of event being audited.
     *
     * @return The type of event being audited.
     */
    public AuditEvent getEvent() {
        return event;
    }

    /**
     * Gets the message sender.
     *
     * @return The message sender.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Gets the message receiver.
     *
     * @return The message receiver.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets the event time.
     *
     * @return The event time
     */
    public Date getEventTime() {
        return eventTime;
    }

    /**
     * Gets the message content.
     *
     * @return The message content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the message security label.
     *
     * @return The message security label.
     */
    public String getSecurityLabel() {
        return securityLabel;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("AuditMessage=[");
        buffer.append("auditEvent=");
        buffer.append(this.event);
        buffer.append(", sender=");
        buffer.append(this.sender);
        buffer.append(", receiver=");
        buffer.append(this.receiver);
        buffer.append(", eventTime=");
        buffer.append(this.eventTime);
        buffer.append(", content=");
        buffer.append(this.content);
        buffer.append(", securityLabel=");
        buffer.append(this.securityLabel);
        buffer.append("]");
        return buffer.toString();
    }

}
