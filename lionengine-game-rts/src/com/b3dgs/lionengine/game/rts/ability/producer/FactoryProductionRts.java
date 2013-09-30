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
package com.b3dgs.lionengine.game.rts.ability.producer;

import com.b3dgs.lionengine.game.FactoryGame;
import com.b3dgs.lionengine.game.SetupGame;
import com.b3dgs.lionengine.game.purview.Configurable;

/**
 * Represents the production factory. Designed to return a producible instance from its id.
 * 
 * @param <T> The entity enum type used.
 * @param <C> The cost type used.
 * @param <P> The producible type used.
 */
public abstract class FactoryProductionRts<T extends Enum<T>, C extends ProductionCostRts, P extends Producible<T, C>>
        extends FactoryGame<T, SetupGame>
{
    /**
     * Constructor.
     * 
     * @param keyType The class of the enum type defined.
     */
    public FactoryProductionRts(Class<T> keyType)
    {
        super(keyType);
    }

    /**
     * Create a new producible from the entity id.
     * 
     * @param id The entity id.
     * @return The producible instance.
     */
    public abstract P createProducible(T id);

    /**
     * Create a new producible from the entity id.
     * 
     * @param id The entity id.
     * @param tx The producible horizontal tile.
     * @param ty The producible vertical tile.
     * @return The producible instance.
     */
    public abstract P createProducible(T id, int tx, int ty);

    /**
     * Get a configurable reference from its id.
     * 
     * @param id The reference id.
     * @return The configurable reference.
     */
    public Configurable getConfig(T id)
    {
        return getSetup(id).configurable;
    }
}
