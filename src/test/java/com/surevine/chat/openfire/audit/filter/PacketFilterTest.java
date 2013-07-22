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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jivesoftware.openfire.multiplex.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xmpp.muc.JoinRoom;
import org.xmpp.muc.LeaveRoom;
import org.xmpp.packet.IQ;
import org.xmpp.packet.Message;
import org.xmpp.packet.Presence;

/**
 * Test case for the {@link PacketFilter} class.
 */
public class PacketFilterTest {

    /**
     * The <code>PacketFilter</code> under test.
     */
    private static final PacketFilter PACKET_FILTER = PacketFilter
            .getInstance();

    /**
     * A normal message type.
     */
    private static Message normalMessage;

    /**
     * A chat message type.
     */
    private static Message chatMessage;

    /**
     * A group chat message type.
     */
    private static Message groupMessage;

    /**
     * An error message type.
     */
    private static Message errorMessage;

    /**
     * A headline message type.
     */
    private static Message headlineMessage;

    /**
     * A presence type.
     */
    private static Presence presence;

    /**
     * A join room presence type.
     */
    private static JoinRoom joinRoom;

    /**
     * A leave room presence type.
     */
    private static LeaveRoom leaveRoom;

    /**
     * An IQ packet type.
     */
    private static IQ iq;

    /**
     * A route packet type.
     */
    private static Route route;

    /**
     * Sets up the test fixtures.
     */
    @Before
    public void setup() {
        normalMessage = new Message();
        normalMessage.setType(Message.Type.normal);
        chatMessage = new Message();
        chatMessage.setType(Message.Type.chat);
        groupMessage = new Message();
        groupMessage.setType(Message.Type.groupchat);
        errorMessage = new Message();
        errorMessage.setType(Message.Type.error);
        headlineMessage = new Message();
        headlineMessage.setType(Message.Type.headline);
        presence = new Presence();
        joinRoom = new JoinRoom("a", "b");
        leaveRoom = new LeaveRoom("a", "b");
        iq = new IQ();
        route = new Route("id");
    }

    /**
     * Tears down the test fixtures.
     */
    @After
    public void teardown() {
        normalMessage = null;
        chatMessage = null;
        groupMessage = null;
        errorMessage = null;
        headlineMessage = null;
        presence = null;
        joinRoom = null;
        leaveRoom = null;
        iq = null;
        route = null;
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a <code>null</code>
     * packet.
     */
    @Test
    public void testNullPacket() {
        assertTrue("Null packet has not been filtered", PACKET_FILTER
                .filter(null));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters an IQ packet.
     */
    @Test
    public void testIQPacket() {
        assertTrue("IQ packet has not been filtered", PACKET_FILTER.filter(iq));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a Route packet.
     */
    @Test
    public void testRoutePacket() {
        assertTrue("Route packet has not been filtered", PACKET_FILTER
                .filter(route));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a Presence packet.
     */
    @Test
    public void testPresencePacket() {
        assertTrue("Presence packet has not been filtered", PACKET_FILTER
                .filter(presence));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a JoinRoom packet.
     */
    @Test
    public void testJoinRoomPacket() {
        assertFalse("JoinRoom packet has been filtered", PACKET_FILTER
                .filter(joinRoom));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a LeaveRoom packet.
     */
    @Test
    public void testLeaveRoomPacket() {
        assertFalse("LeaveRoom packet has been filtered", PACKET_FILTER
                .filter(leaveRoom));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a Normal Message
     * packet.
     */
    @Test
    public void testNormalMessagePacket() {
        assertFalse("NormalMessage packet has been filtered", PACKET_FILTER
                .filter(normalMessage));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a Chat Message
     * packet.
     */
    @Test
    public void testChatMessagePacket() {
        assertFalse("ChatMessage packet has been filtered", PACKET_FILTER
                .filter(chatMessage));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a Group Chat Message
     * packet.
     */
    @Test
    public void testGroupChatMessagePacket() {
        assertFalse("GroupChatMessage packet has been filtered", PACKET_FILTER
                .filter(groupMessage));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters an Error Message
     * packet.
     */
    @Test
    public void testErrorMessagePacket() {
        assertTrue("ErrorMessage packet has been filtered", PACKET_FILTER
                .filter(errorMessage));
    }

    /**
     * Tests that a {@link PacketFilter} correctly filters a Headline Message
     * packet.
     */
    @Test
    public void testHeadlineMessagePacket() {
        assertTrue("HeadlineMessage packet has been filtered", PACKET_FILTER
                .filter(headlineMessage));
    }

}
