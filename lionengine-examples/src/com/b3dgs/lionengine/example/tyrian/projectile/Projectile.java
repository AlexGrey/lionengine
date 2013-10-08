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
package com.b3dgs.lionengine.example.tyrian.projectile;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.SpriteTiled;
import com.b3dgs.lionengine.example.tyrian.entity.Entity;
import com.b3dgs.lionengine.game.CameraGame;
import com.b3dgs.lionengine.game.CollisionData;
import com.b3dgs.lionengine.game.SetupSurfaceGame;
import com.b3dgs.lionengine.game.projectile.ProjectileGame;

/**
 * Projectile base implementation.
 */
public abstract class Projectile
        extends ProjectileGame<Entity, Entity>
{
    /** Default collision. */
    private static final CollisionData COLLISION = new CollisionData(10, -4, 4, 4, false);

    /** Projectile surface. */
    private final SpriteTiled sprite;

    /**
     * @see ProjectileGame#ProjectileGame(SetupSurfaceGame)
     */
    Projectile(SetupSurfaceGame setup)
    {
        super(setup);
        sprite = Drawable.loadSpriteTiled(setup.surface, 12, 14);
        sprite.load(false);
        setCollision(Projectile.COLLISION);
    }

    /*
     * ProjectileGame
     */

    @Override
    public void render(Graphic g, CameraGame camera)
    {
        sprite.render(g, camera.getViewpointX(getLocationIntX()), camera.getViewpointY(getLocationIntY()));
        if (!camera.isVisible(this))
        {
            destroy();
        }
    }

    @Override
    public void onHit(Entity entity, int damages)
    {
        entity.destroy();
        destroy();
    }

    @Override
    protected void updateMovement(double extrp, double vecX, double vecY)
    {
        moveLocation(extrp, vecX, vecY);
    }
}