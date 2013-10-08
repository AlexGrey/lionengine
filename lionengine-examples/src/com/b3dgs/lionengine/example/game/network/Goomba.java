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

import com.b3dgs.lionengine.Timing;
import com.b3dgs.lionengine.game.SetupSurfaceGame;
import com.b3dgs.lionengine.game.entity.EntityGame;
import com.b3dgs.lionengine.network.message.NetworkMessage;

/**
 * Goomba implementation.
 */
class Goomba
        extends Entity
{
    /** Die timer. */
    private final Timing timerDie;
    /** Network id. */
    private short networkId;

    /**
     * Standard constructor.
     * 
     * @param setup setup reference.
     * @param map The map reference.
     * @param desiredFps desired fps.
     * @param server <code>true</code> if is server, <code>false</code> if client.
     */
    public Goomba(SetupSurfaceGame setup, Map map, int desiredFps, boolean server)
    {
        super(setup, TypeEntity.goomba, map, desiredFps, server);
        timerDie = new Timing();
        jumpForceValue = 9.0;
        movementSpeedValue = 0.75;
        right = true;
    }

    /**
     * Set the network id.
     * 
     * @param id The network id.
     */
    public void setNetworkId(short id)
    {
        networkId = id;
    }

    @Override
    protected void handleActions(double extrp)
    {
        super.handleActions(extrp);
        if (dead && timerDie.elapsed(1000))
        {
            destroy();
        }
    }

    @Override
    protected void handleMovements(double extrp)
    {
        super.handleMovements(extrp);
        movementForce.reachForce(extrp, movementForceDest, 0.3, 0.1);
    }

    @Override
    protected void handleCollisions(double extrp)
    {
        super.handleCollisions(extrp);
        // Send correct location if moving, or just idle
        if (server)
        {
            // Network location timer
            if (networkLocation.elapsed(500) && (state == EntityState.WALK || state == EntityState.IDLE))
            {
                final MessageEntity message = new MessageEntity(networkId);
                message.addAction(MessageEntityElement.LOCATION_X, getLocationIntX());
                message.addAction(MessageEntityElement.LOCATION_Y, getLocationIntY());
                addNetworkMessage(message);
                networkLocation.start();
            }
        }
    }

    @Override
    protected void onHorizontalCollision()
    {
        // Invert movement in case of collision
        if (right)
        {
            right = false;
            left = true;
        }
        else
        {
            left = false;
            right = true;
        }
        if (server)
        {
            final MessageEntity message = new MessageEntity(networkId);
            message.addAction(MessageEntityElement.RIGHT, right);
            message.addAction(MessageEntityElement.LEFT, left);
            message.addAction(MessageEntityElement.LOCATION_X, getLocationIntX());
            message.addAction(MessageEntityElement.LOCATION_Y, getLocationIntY());
            addNetworkMessage(message);
        }
    }

    @Override
    public void onHurtBy(EntityGame entity, int damages)
    {
        if (!dead)
        {
            doDie();
        }
    }

    @Override
    public void onHitThat(Entity entity)
    {
        // Nothing to do
    }

    /**
     * Die goomba.
     */
    public void doDie()
    {
        dead = true;
        right = false;
        left = false;
        resetMovementSpeed();
        timerDie.start();
    }

    @Override
    public void applyMessage(NetworkMessage message)
    {
        if (!(message instanceof MessageEntity))
        {
            return;
        }
        final MessageEntity msg = (MessageEntity) message;
        if (msg.getEntityId() == networkId)
        {
            super.applyMessage(message);
            if (msg.hasAction(MessageEntityElement.DIE))
            {
                doDie();
            }
        }
    }
}