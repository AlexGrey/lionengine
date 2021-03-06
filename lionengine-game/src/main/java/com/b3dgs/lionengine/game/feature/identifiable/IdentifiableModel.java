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
package com.b3dgs.lionengine.game.feature.identifiable;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.game.feature.FeatureModel;

/**
 * Default identifiable implementation. Handle a list of unique ID, provide the next free ID, and recycle destroyed ID.
 */
public class IdentifiableModel extends FeatureModel implements Identifiable
{
    /** ID used (list of active id used). */
    private static final Collection<Integer> IDS = new HashSet<Integer>();
    /** Recycle ID (reuse previous removed object ID). */
    private static final Queue<Integer> RECYCLE = new ArrayDeque<Integer>();
    /** Free ID error. */
    private static final String ERROR_FREE_ID = "No more free id available !";
    /** Last ID used (last maximum id value). */
    private static int lastId;

    /**
     * Get the next unused ID.
     * 
     * @return The next unused ID.
     * @throws LionEngineException If there is more than {@link Integer#MAX_VALUE} at the same time.
     */
    private static Integer getFreeId()
    {
        if (!RECYCLE.isEmpty())
        {
            final Integer id = RECYCLE.poll();
            IDS.add(id);
            return id;
        }
        if (IDS.size() >= Integer.MAX_VALUE)
        {
            throw new LionEngineException(ERROR_FREE_ID);
        }
        while (IDS.contains(Integer.valueOf(lastId)))
        {
            lastId++;
        }
        final Integer id = Integer.valueOf(lastId);
        IDS.add(id);
        return id;
    }

    /** Listeners. */
    private final Collection<IdentifiableListener> listeners = new HashSet<IdentifiableListener>(1);
    /** Unique ID. */
    private final Integer id = getFreeId();
    /** Destroy request flag. */
    private boolean destroy;
    /** Destroyed flag. */
    private boolean destroyed;

    /**
     * Create the identifiable with a unique ID.
     * 
     * @throws LionEngineException If no free ID available.
     */
    public IdentifiableModel()
    {
        super();
    }

    /*
     * Identifiable
     */

    @Override
    public void addListener(IdentifiableListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IdentifiableListener listener)
    {
        listeners.remove(listener);
    }

    @Override
    public Integer getId()
    {
        if (destroyed)
        {
            return null;
        }
        return id;
    }

    @Override
    public void destroy()
    {
        if (!destroy)
        {
            destroy = true;
            for (final IdentifiableListener listener : listeners)
            {
                listener.notifyDestroyed(id);
            }
        }
    }

    @Override
    public void notifyDestroyed()
    {
        destroyed = true;
        IDS.remove(id);
        RECYCLE.add(id);
        listeners.clear();
    }
}
