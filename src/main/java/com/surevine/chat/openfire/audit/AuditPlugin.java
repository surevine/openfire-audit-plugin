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

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.openfire.container.Plugin;
import org.jivesoftware.openfire.container.PluginClassLoader;
import org.jivesoftware.openfire.container.PluginManager;
import org.jivesoftware.openfire.interceptor.InterceptorManager;
import org.jivesoftware.openfire.interceptor.PacketInterceptor;
import org.jivesoftware.openfire.interceptor.PacketRejectedException;
import org.jivesoftware.openfire.session.Session;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.Presence;

import com.surevine.chat.openfire.audit.filter.PacketFilter;
import com.surevine.chat.openfire.audit.service.IAuditService;

/**
 * This class is an Openfire plugin extension that will be used to audit
 * messages of Openfire system events.
 */
public class AuditPlugin implements Plugin, PacketInterceptor {

    /**
     * Logging instance.
     */
    private static final Log LOG = LogFactory.getLog(AuditPlugin.class);

    /**
     * The Spring bean definition file that defines the Spring services.
     */
    private static final String BEAN_DEFINITION_FILE = "/auditBeans.xml";

    /**
     * The Spring bean name for the audit service.
     */
    private static final String AUDIT_SERVICE = "auditService";

    /**
     * The plugin manager that manages all the Openfire plugins.
     */
    private static PluginManager pluginManager;

    /**
     * The interceptor manager that manages all the Openfire interceptors.
     */
    private InterceptorManager interceptorManager;

    /**
     * The audit service that will be used to handle the actual auditing.
     */
    private IAuditService auditService;

    /**
     * The factory for creating <code>AuditMessage</code> objects.
     */
    private AuditMessageFactory auditMessageFactory;

    /**
     * The packet filter used to determine which packets to process.
     */
    private PacketFilter packetFilter;

    /**
     * Gets the {@link PluginManager} registered with this plugin.
     *
     * @return The plugin manager.
     */
    public static PluginManager getPluginManager() {
        return pluginManager;
    }

    /**
     * {@inheritDoc}.
     */
    public void initializePlugin(final PluginManager manager,
            final File pluginDirectory) {
        pluginManager = manager;

        interceptorManager = InterceptorManager.getInstance();
        ClassPathResource resource = new ClassPathResource(BEAN_DEFINITION_FILE);
        XmlBeanFactory xmlBeanFactory = new XmlBeanFactory(resource);
        auditService = (IAuditService) xmlBeanFactory.getBean(AUDIT_SERVICE);
        auditMessageFactory = new AuditMessageFactory();
        packetFilter = PacketFilter.getInstance();

        // Register this plugin with the interceptor manager
        interceptorManager.addInterceptor(this);

        if (LOG.isInfoEnabled()) {
            LOG.info("Audit Plugin Loaded");
        }
    }

    /**
     * {@inheritDoc}.
     */
    public void destroyPlugin() {
        // Unregister the plugin with the interceptor manager
        interceptorManager.removeInterceptor(this);
        auditService = null;
        auditMessageFactory = null;
        packetFilter = null;
        if (LOG.isInfoEnabled()) {
            LOG.info("Audit Plugin Unloaded");
        }
    }

    /**
     * {@inheritDoc}.
     */
    public void interceptPacket(final Packet packet, final Session session,
            final boolean incoming, final boolean processed)
            throws PacketRejectedException {
        // If the packet has already been processed by the server then ignore it
        if (processed) {
            return;
        }

        // Check whether to filter the packet if so ignore it
        if (packetFilter.filter(packet)) {
            return;
        }

        AuditMessage auditMessage = null;
        // Create the audit message
        if (packet instanceof Message) {
            final Message message = (Message) packet;
            final Message.Type messageType = message.getType();
            if (messageType == Message.Type.normal || incoming) {
                auditMessage = auditMessageFactory.createAuditMessage(message);
            } else {
                return;
            }
        } else if (packet instanceof Presence && incoming) {
            Presence presence = (Presence) packet;
            auditMessage = auditMessageFactory.createAuditMessage(presence);
        } else {
            return;
        }

        // Audit the message
        auditService.audit(auditMessage);
    }
    
    public AuditMessageFactory getAuditMessageFactory() {
    	return auditMessageFactory;
    }

}
