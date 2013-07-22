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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.xmpp.muc.JoinRoom;
import org.xmpp.muc.LeaveRoom;
import org.xmpp.packet.Message;
import org.xmpp.packet.Presence;

import com.surevine.chat.common.xmpp.security.IXmppSecurityLabelExtension;

/**
 * This factory class produces {@link AuditMessage} objects from incoming XMPP
 * {@link org.xmpp.packet.Packet} objects.
 */
public class AuditMessageFactory {

    /**
     * Logging instance.
     */
    private static final Log LOG = LogFactory.getLog(AuditMessageFactory.class);

    /**
     * Element name for XEP display marking extension.
     */
    private static final String DISPLAY_MARKING = "displaymarking";
    
    
    public AuditMessage createAuditMessageForSULogin(String username, boolean success) {
    	if (LOG.isDebugEnabled()) {
            LOG.debug("Creating an audit message from a superuser login to: " + username);
    	}
    	AuditEvent event = null;
    	if (success) {
    		event = AuditEvent.SUPERUSER_LOGIN;
    	}
    	else {
    		event = AuditEvent.SUPERUSER_LOGIN_FAIL;
    	}
    	String sender = "SuperUser";
    	String receiver = username;
    	Date eventTime = new Date();
        return new AuditMessage(event, sender, receiver, eventTime);

    }

    /**
     * Creates an {@link AuditMessage} from an XMPP {@link Presence} packet. This method only
     * supports the creation of <code>AuditMessage</code> objects from {@link JoinRoom} and
     * {@link LeaveRoom} subclasses.
     * 
     * @param presence
     *            The XMPP presence packet.
     * @return The <code>AuditMessage</code> for the provided <code>Presence</code> packet or an
     *         <code>AuditException</code> if the <code>Presence</code> packet is <code>null</code>
     *         or an unsupported subclass.
     */
    public AuditMessage createAuditMessage(final Presence presence) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating an audit message from a presence packet: " + presence);
        }
        if (presence == null) {
            throw new AuditException(
                    "Unable to create an audit message from a null presence packet");
        }

        /*
         * We are only interested in the presence types for joining or leaving a room. If we receive
         * any other types then an error has occured with the packet filtering.
         */
        AuditEvent event = null;

        if (presence instanceof JoinRoom) {
            event = AuditEvent.JOIN_ROOM;
        } else if (presence instanceof LeaveRoom) {
            event = AuditEvent.LEAVE_ROOM;
        } else {
            throw new AuditException(
                    "Unsupported presence type found when creating an audit message");
        }

        String sender = presence.getFrom().toString();
        String receiver = presence.getTo().toString();
        Date eventTime = new Date();

        return new AuditMessage(event, sender, receiver, eventTime);
    }

    /**
     * Creates an {@link AuditMessage} from an XMPP {@link Message} packet. This method only
     * supports the creation of <code>AuditMessage</code> objects from <code>Message</code> objects
     * of the type {@link Message.Type.normal} , {@link Message.Type.chat} and
     * {@link Message.Type.groupchat}.
     * 
     * @param message
     *            The XMPP Message packet.
     * @return The <code>AuditMessage</code> for the provided <code>Message</code> or an
     *         <code>AuditException</code> if the <code>Message</code> is <code>null</code> or an
     *         unsupported type.
     */
    public AuditMessage createAuditMessage(final Message message) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating an audit message from a message packet: " + message);
        }
        if (message == null) {
            throw new AuditException("Unable to create an audit message from a null message packet");
        }

        /*
         * We are only interested in the message types for normal, chat or groupchat. If we receive
         * any other types then an error has occurred with the packet filtering.
         */
        AuditEvent event = null;
        final Message.Type messageType = message.getType();
        if (messageType == Message.Type.normal) {
            event = AuditEvent.INVITATION;
        } else if (messageType == Message.Type.chat) {
            event = AuditEvent.CHAT_MSG;
        } else if (messageType == Message.Type.groupchat) {
            event = AuditEvent.GROUP_CHAT_MSG;
        } else {
            throw new AuditException(
                    "Unsupported message type found when creating an audit message");
        }

        String sender = message.getFrom().toString();
        String receiver = message.getTo().toString();
        Date eventTime = new Date();
        String content = message.getBody();
        String securityLabel = "";

        try {
            Element element = message.getExtension(
                    IXmppSecurityLabelExtension.XEP_0258_XML_ELEMENT,
                    IXmppSecurityLabelExtension.XEP_0258_XML_NAMESPACE).getElement();
            securityLabel = element.elementText(DISPLAY_MARKING);
        } catch (NullPointerException npe) {
            LOG.info("No SecurityLabelExtension for groupChat");
        }

        return new AuditMessage(event, sender, receiver, eventTime, content, securityLabel);
    }

}
