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
package com.b3dgs.lionengine.core.awt;

import org.junit.Assert;
import org.junit.Test;

import com.b3dgs.lionengine.Constant;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.core.Engine;
import com.b3dgs.lionengine.core.Version;

/**
 * Test the engine class.
 */
public class EngineAwtTest
{
    /**
     * Test the engine start without resources.
     */
    @Test(expected = LionEngineException.class)
    public void testEngineNullResources()
    {
        EngineAwt.start(EngineAwtTest.class.getName(), Version.DEFAULT, (String) null);
        try
        {
            Assert.assertTrue(Engine.isStarted());
            EngineAwt.start(EngineAwtTest.class.getName(), Version.DEFAULT, (String) null);
        }
        finally
        {
            Engine.terminate();
        }
    }

    /**
     * Test the engine start with resources.
     */
    @Test(expected = LionEngineException.class)
    public void testEngineResources()
    {
        EngineAwt.start(EngineAwtTest.class.getName(), Version.DEFAULT, Constant.EMPTY_STRING);
        try
        {
            Assert.assertTrue(Engine.isStarted());
            EngineAwt.start(EngineAwtTest.class.getName(), Version.DEFAULT, Constant.EMPTY_STRING);
        }
        finally
        {
            Engine.terminate();
        }
    }

    /**
     * Test the engine start with class.
     */
    @Test(expected = LionEngineException.class)
    public void testEngineClass()
    {
        EngineAwt.start(EngineAwtTest.class.getName(), Version.DEFAULT, EngineAwtTest.class);
        try
        {
            Assert.assertTrue(Engine.isStarted());
            EngineAwt.start(EngineAwtTest.class.getName(), Version.DEFAULT, EngineAwtTest.class);
        }
        finally
        {
            Engine.terminate();
        }
    }
}
