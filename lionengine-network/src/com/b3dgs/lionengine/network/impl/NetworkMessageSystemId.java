/*
 * Copyright (C) 2013 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine.network.impl;

/**
 * List of messages system flag. This allows to filter data on message received depending of the case.
 */
interface NetworkMessageSystemId
{
    /** First connection step (client if preparing its connection with the server). */
    final byte CONNECTING = -120;
    /** Last connection step (client is now connected to the server properly). */
    final byte CONNECTED = -110;
    /** Ping message. */
    final byte PING = -105;
    /** Client is kicked. */
    final byte KICKED = -100;
    /** Inform about a new connected client. */
    final byte OTHER_CLIENT_CONNECTED = -90;
    /** Disconnect other client. */
    final byte OTHER_CLIENT_DISCONNECTED = -80;
    /** Other client Name changes. */
    final byte OTHER_CLIENT_RENAMED = -70;
    /** User message. */
    final byte USER_MESSAGE = -60;
}
