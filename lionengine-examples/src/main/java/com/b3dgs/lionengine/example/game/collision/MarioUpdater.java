/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.game.collision;

import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.core.awt.Keyboard;
import com.b3dgs.lionengine.game.Axis;
import com.b3dgs.lionengine.game.Direction;
import com.b3dgs.lionengine.game.Force;
import com.b3dgs.lionengine.game.Service;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.collision.object.Collidable;
import com.b3dgs.lionengine.game.collision.tile.TileCollidable;
import com.b3dgs.lionengine.game.collision.tile.TileCollidableListener;
import com.b3dgs.lionengine.game.feature.FeatureModel;
import com.b3dgs.lionengine.game.handler.Handlable;
import com.b3dgs.lionengine.game.object.feature.body.Body;
import com.b3dgs.lionengine.game.object.feature.refreshable.Refreshable;
import com.b3dgs.lionengine.game.object.feature.transformable.Transformable;
import com.b3dgs.lionengine.game.tile.Tile;

/**
 * Updating of our controllable entity.
 */
class MarioUpdater extends FeatureModel implements Refreshable, TileCollidableListener
{
    private final Force movement = new Force();
    private final Force jump = new Force();

    @Service private Body body;
    @Service private Transformable transformable;
    @Service private Collidable collidable;
    @Service private TileCollidable tileCollidable;
    @Service private Keyboard keyboard;

    @Override
    public void prepare(Handlable owner, Services services)
    {
        super.prepare(owner, services);

        collidable.setOrigin(Origin.CENTER_BOTTOM);
        tileCollidable.addListener(this);

        jump.setVelocity(0.1);
        jump.setDestination(0.0, 0.0);
        transformable.teleport(80, 32);

        body.setDesiredFps(60);
        body.setMass(2.0);
        body.setVectors(movement, jump);
    }

    @Override
    public void update(double extrp)
    {
        movement.setDirection(Direction.ZERO);
        if (keyboard.isPressed(Keyboard.LEFT))
        {
            movement.setDirection(-2, 0);
        }
        if (keyboard.isPressed(Keyboard.RIGHT))
        {
            movement.setDirection(2, 0);
        }
        if (keyboard.isPressedOnce(Keyboard.UP))
        {
            jump.setDirection(0.0, 8.0);
        }

        movement.update(extrp);
        jump.update(extrp);
        body.update(extrp);
        tileCollidable.update(extrp);
        collidable.update(extrp);

        if (transformable.getY() < 0)
        {
            transformable.teleportY(80);
            body.resetGravity();
        }
    }

    @Override
    public void notifyTileCollided(Tile tile, Axis axis)
    {
        if (Axis.Y == axis)
        {
            body.resetGravity();
        }
    }
}
