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
package com.b3dgs.lionengine.example.game.network;

import java.util.Iterator;
import java.util.LinkedList;

import com.b3dgs.lionengine.Align;
import com.b3dgs.lionengine.ColorRgba;
import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.Text;
import com.b3dgs.lionengine.TextStyle;
import com.b3dgs.lionengine.core.Key;
import com.b3dgs.lionengine.core.UtilityImage;
import com.b3dgs.lionengine.network.ConnectionListener;
import com.b3dgs.lionengine.network.message.NetworkMessageChat;
import com.b3dgs.lionengine.network.purview.NetworkChat;

/**
 * Chat implementation.
 */
class Chat
        extends NetworkChat
        implements ConnectionListener
{
    /** Background. */
    private static final ColorRgba BACKGROUND = new ColorRgba(128, 128, 128, 192);
    /** Background writing. */
    private static final ColorRgba BACKGROUND_WRITING = new ColorRgba(64, 64, 64, 192);
    /** Text. */
    private final Text text;
    /** Mario reference. */
    private final World<?> world;

    /**
     * Constructor.
     * 
     * @param world The world reference.
     */
    public Chat(World<?> world)
    {
        super(TypeMessage.MESSAGE_CHAT);
        this.world = world;
        text = UtilityImage.createText(Text.DIALOG, 9, TextStyle.NORMAL);
        setKeyValidate(Key.ENTER.intValue());
        setKeySpace(Key.SPACE.intValue());
    }

    @Override
    public void render(Graphic g)
    {
        g.setColor(Chat.BACKGROUND);
        g.drawRect(0, 180, 120, 60, true);
        g.setColor(Chat.BACKGROUND_WRITING);
        g.drawRect(0, 228, 120, 12, true);
        final Iterator<String> messages = new LinkedList<>(getMessages()).descendingIterator();
        int i = 1;
        while (messages.hasNext())
        {
            text.draw(g, 0, 230 - i * 12, Align.LEFT, messages.next());
            i++;
        }
        text.draw(g, 0, 230, Align.LEFT, ">:" + getWriting());
    }

    @Override
    protected boolean canAddChar(char c)
    {
        return true;
    }

    @Override
    protected String getMessage(NetworkMessageChat message)
    {
        return new StringBuilder(world.getClientName(message.getClientId())).append(" says: ")
                .append(message.getMessage()).toString();
    }

    @Override
    protected boolean canSendMessage(String message)
    {
        if (message.startsWith("/"))
        {
            world.applyCommand(message);
            return false;
        }
        return true;
    }

    /*
     * ClientListener
     */

    @Override
    public void notifyClientConnected(Byte id, String name)
    {
        addMessage(name + " connected");
    }

    @Override
    public void notifyClientDisconnected(Byte id, String name)
    {
        addMessage(name + " disconnected");
    }

    @Override
    public void notifyClientNameChanged(Byte id, String name)
    {
        addMessage(world.getClientName(id.byteValue()) + " renamed as: " + name);
    }

    @Override
    public void notifyConnectionEstablished(Byte id, String name)
    {
        addMessage("Connection established as: " + name);
    }

    @Override
    public void notifyMessageOfTheDay(String messageOfTheDay)
    {
        addMessage(messageOfTheDay);
    }

    @Override
    public void notifyConnectionTerminated(Byte id)
    {
        addMessage("Connection terminated");
    }
}