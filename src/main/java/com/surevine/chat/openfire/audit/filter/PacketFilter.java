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

package com.surevine.chat.openfire.audit.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmpp.muc.JoinRoom;
import org.xmpp.muc.LeaveRoom;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;
import org.xmpp.packet.Message.Type;

/**
 * This class filters XMPP {@link Packet} objects determining whether they
 * should be processed or not.
 */
public final class PacketFilter {

    /**
     * Singleton instance of this class.
     */
    private static final PacketFilter INSTANCE = new PacketFilter();

    /**
     * Logging instance.
     */
    private static final Log LOG = LogFactory.getLog(PacketFilter.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private PacketFilter() {
        // NO-OP
    }

    /**
     * Gets the singleton instance of ths class.
     *
     * @return The singleton instance of this class.
     */
    public static PacketFilter getInstance() {
        return INSTANCE;
    }

    /**
     * Filters XMPP Packets based on whether the packets should be processed or
     * not. The filtering process is determined by the type of
     * <code>Packet</code> that is being passed into the method.
     *
     * @param packet
     *            The XMPP packet to be filtered.
     * @return <code>true</code> if the packet is <code>null</code> or should be
     *         filtered out, otherwise <code>false</code>.
     */
    public boolean filter(final Packet packet) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Filtering XMPP packet");
        }

        if (packet == null) {
            return true;
        } else if (packet instanceof Presence) {
            return (!(packet instanceof JoinRoom) && !(packet instanceof LeaveRoom));
        } else if (packet instanceof Message) {
            final Message message = (Message) packet;
            final Message.Type messageType = message.getType();
            return (messageType != Type.normal && messageType != Type.chat && messageType != Type.groupchat);
        } else {
            return true;
        }
    }

}
