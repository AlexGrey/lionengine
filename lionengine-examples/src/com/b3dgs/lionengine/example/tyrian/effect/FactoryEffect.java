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
package com.b3dgs.lionengine.example.tyrian.effect;

import java.lang.reflect.InvocationTargetException;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.game.SetupSurfaceGame;
import com.b3dgs.lionengine.game.effect.FactoryEffectGame;

/**
 * Factory effect implementation.
 */
public final class FactoryEffect
        extends FactoryEffectGame<EffectType, SetupSurfaceGame, Effect>
{
    /**
     * Create an effect from its type.
     * 
     * @param type The item type.
     * @param setup The setup reference.
     * @return The item instance.
     */
    public static Effect createEntity(EffectType type, SetupSurfaceGame setup)
    {
        try
        {
            final StringBuilder clazz = new StringBuilder(FactoryEffect.class.getPackage().getName());
            clazz.append('.').append(type.asClassName());
            return (Effect) Class.forName(clazz.toString()).getConstructor(SetupSurfaceGame.class).newInstance(setup);
        }
        catch (InstantiationException
               | IllegalAccessException
               | IllegalArgumentException
               | InvocationTargetException
               | NoSuchMethodException
               | SecurityException
               | ClassCastException
               | ClassNotFoundException exception)
        {
            throw new LionEngineException(exception, "Unkown effect: " + type.asClassName());
        }
    }

    /**
     * Constructor.
     */
    public FactoryEffect()
    {
        super(EffectType.class);
        loadAll(EffectType.values());
    }

    /*
     * FactoryEffectGame
     */

    @Override
    public Effect createEffect(EffectType type)
    {
        return FactoryEffect.createEntity(type, getSetup(type));
    }

    @Override
    protected SetupSurfaceGame createSetup(EffectType id)
    {
        return new SetupSurfaceGame(Media.get("effects", id.toString() + ".xml"));
    }
}