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
package com.b3dgs.lionengine.mock;

import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Resolution;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Loader;
import com.b3dgs.lionengine.core.Sequence;

/**
 * Async interrupt sequence.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class SequenceAsyncInterruptMock
        extends Sequence
{
    /**
     * Constructor.
     * 
     * @param loader The loader reference.
     */
    public SequenceAsyncInterruptMock(Loader loader)
    {
        super(loader, new Resolution(320, 240, 60));
    }

    @Override
    protected void load()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch (final InterruptedException exception)
        {
            throw new LionEngineException(exception);
        }
    }

    @Override
    protected void update(double extrp)
    {
        end(SequenceSingleMock.class);
    }

    @Override
    protected void render(Graphic g)
    {
        // Mock
    }
}