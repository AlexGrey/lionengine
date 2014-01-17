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
package com.b3dgs.lionengine.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test object type utility class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class ObjectTypeUtilityTest
{
    /**
     * Test object type utility class.
     * 
     * @throws Exception If error.
     */
    @Test
    public void testObjectTypeUtilityClass() throws Exception
    {
        final Constructor<ObjectTypeUtility> constructor = ObjectTypeUtility.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        try
        {
            final ObjectTypeUtility objectTypeUtility = constructor.newInstance();
            Assert.assertNotNull(objectTypeUtility);
            Assert.fail();
        }
        catch (final InvocationTargetException exception)
        {
            // Success
        }
    }

    /**
     * Test object type utility functions.
     */
    @Test
    public void testObjectTypeUtility()
    {
        Assert.assertEquals(ObjectTypeUtility.getPathName(Type.TYPE), Type.TYPE.name().toLowerCase(Locale.ENGLISH));
        Assert.assertFalse(ObjectTypeUtility.toString(Type.TYPE).equals(Type.TYPE.toString()));
    }
}
