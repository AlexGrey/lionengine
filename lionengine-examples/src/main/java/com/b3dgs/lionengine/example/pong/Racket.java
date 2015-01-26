/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.pong;

import com.b3dgs.lionengine.ColorRgba;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.core.Core;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.core.Renderable;
import com.b3dgs.lionengine.core.Updatable;
import com.b3dgs.lionengine.game.Force;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.component.ComponentCollisionListener;
import com.b3dgs.lionengine.game.configurer.ConfigCollisions;
import com.b3dgs.lionengine.game.factory.Setup;
import com.b3dgs.lionengine.game.handler.ObjectGame;
import com.b3dgs.lionengine.game.trait.Collidable;
import com.b3dgs.lionengine.game.trait.CollidableModel;
import com.b3dgs.lionengine.game.trait.Transformable;
import com.b3dgs.lionengine.game.trait.TransformableModel;

/**
 * Racket implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
class Racket
        extends ObjectGame
        implements Updatable, Renderable, ComponentCollisionListener
{
    /** Racket media. */
    public static final Media MEDIA = Core.MEDIA.create("Racket.xml");
    /** Racket color. */
    private static final ColorRgba COLOR = ColorRgba.YELLOW;

    /** Transformable model. */
    private final Transformable transformable;
    /** Collidable model. */
    private final Collidable collidable;
    /** Current force. */
    private final Force force;
    /** Ball reference. */
    private Ball ball;

    /**
     * {@link ObjectGame#ObjectGame(Setup, Services)}
     */
    public Racket(Setup setup, Services context) throws LionEngineException
    {
        super(setup, context);
        transformable = new TransformableModel(this, setup.getConfigurer());
        addTrait(transformable);

        collidable = new CollidableModel(this, context);
        addTrait(collidable);

        force = new Force();

        collidable.addCollision(ConfigCollisions.create(setup.getConfigurer()).getCollision("default"));
        collidable.setCollisionVisibility(true);
        transformable.teleportY(240 / 2 - transformable.getHeight() / 2);
    }

    /**
     * Set the racket side on screen.
     * 
     * @param left <code>true</code> for left side, <code>false</code> for right side.
     */
    public void setSide(boolean left)
    {
        if (left)
        {
            transformable.teleportX(0);
        }
        else
        {
            transformable.teleportX(320 - transformable.getWidth());
        }
    }

    /**
     * Set the ball reference.
     * 
     * @param ball The ball reference.
     */
    public void setBall(Ball ball)
    {
        this.ball = ball;
    }

    @Override
    public void update(double extrp)
    {
        force.update(extrp);
        transformable.moveLocation(extrp, force);

        final Transformable object = ball.getTrait(Transformable.class);
        transformable.moveLocation(extrp, 0.0, object.getY() - transformable.getY() < 0 ? -1.0 : 1.0);
        collidable.update(extrp);
    }

    @Override
    public void render(Graphic g)
    {
        g.setColor(COLOR);
        g.drawRect((int) transformable.getX(), (int) transformable.getY(), transformable.getWidth(),
                transformable.getHeight(), true);
    }

    @Override
    public void notifyCollided(Collidable collidable)
    {
        // Nothing to do
    }
}
