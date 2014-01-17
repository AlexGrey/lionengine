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
package com.b3dgs.lionengine.game.rts.ability.producer;

import com.b3dgs.lionengine.game.FactoryObjectGame;
import com.b3dgs.lionengine.game.rts.entity.EntityRts;

/**
 * List of services used by the producer.
 * 
 * @param <T> The entity enum type used.
 * @param <C> The cost type used.
 * @param <P> The producible type used.
 * @param <E> The entity type used.
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public interface ProducerUsedServices<T extends Enum<T>, C extends ProductionCostRts, P extends Producible<T, C>, E extends EntityRts>
        extends ProducerListener<T, C, P, E>
{
    /**
     * Condition to start production check (able to produce).
     * <p>
     * Example:
     * </p>
     * <ul>
     * <li>Did the player have enough resources ?</li>
     * <li>Is the owner alive ?</li>
     * </ul>
     * 
     * @param producible The producible to check.
     * @return <code>true</code> if able to produce, <code>false</code> else.
     */
    boolean canProduce(P producible);

    /**
     * Condition to make production start.
     * <p>
     * For example:
     * <ul>
     * <li>The owner has enough resources regarding the producible cost</li>
     * <li>The owner is close enough to the building location</li>
     * </ul>
     * </p>
     * 
     * @param producible The productible reference.
     * @return <code>true</code> if can start production, <code>false</code> else.
     */
    boolean canBeProduced(P producible);

    /**
     * Get entity to produce from its id. The common usage is to return a new entity instance by using the factory (
     * {@link FactoryObjectGame#create(Enum)}.
     * 
     * @param id The entity id.
     * @return The entity reference.
     */
    E getEntityToProduce(T id);

    /**
     * Get the number of steps done per seconds (the production speed).
     * 
     * @return The number of steps per seconds.
     */
    int getStepsPerSecond();

    /**
     * Get the player id.
     * 
     * @return The player id.
     */
    int getPlayerId();
}
