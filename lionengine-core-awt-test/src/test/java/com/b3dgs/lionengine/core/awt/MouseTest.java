/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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

import java.awt.Label;
import java.awt.event.MouseEvent;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.Test;

import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.Resolution;

/**
 * Test the mouse class.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class MouseTest
{
    /**
     * Create a mouse and configure for test.
     * 
     * @return The created mouse.
     */
    private static MouseAwt createMouse()
    {
        final Resolution resolution = new Resolution(320, 240, 60);
        final Config config = new Config(resolution, 32, false);
        config.setSource(resolution);

        final MouseAwt mouse = new MouseAwt();
        mouse.setConfig(config);
        mouse.setCenter(160, 120);
        return mouse;
    }

    /**
     * Create a mouse event.
     * 
     * @param click The click mouse.
     * @param x The horizontal location.
     * @param y The vertical location.
     * @return The event instance.
     */
    private static MouseEvent createEvent(int click, int x, int y)
    {
        return new MouseEvent(new Label(), 0, 0L, 0, x, y, 3, false, click);
    }

    /**
     * Test the mouse clicked state.
     */
    @Test
    public void testClicked()
    {
        final MouseAwt mouse = createMouse();

        Assert.assertFalse(mouse.hasClicked(Mouse.LEFT));
        mouse.mousePressed(createEvent(Mouse.LEFT, 0, 0));
        Assert.assertTrue(mouse.hasClicked(Mouse.LEFT));
        mouse.mouseReleased(createEvent(Mouse.LEFT, 0, 0));
        Assert.assertFalse(mouse.hasClicked(Mouse.LEFT));

        Assert.assertFalse(mouse.hasClicked(Mouse.RIGHT));
        mouse.mousePressed(createEvent(Mouse.RIGHT, 0, 0));
        Assert.assertTrue(mouse.hasClicked(Mouse.RIGHT));
        mouse.mouseReleased(createEvent(Mouse.RIGHT, 0, 0));
        Assert.assertFalse(mouse.hasClicked(Mouse.RIGHT));

        Assert.assertFalse(mouse.hasClickedOnce(Mouse.MIDDLE));
        mouse.mousePressed(createEvent(Mouse.MIDDLE, 0, 0));
        Assert.assertTrue(mouse.hasClickedOnce(Mouse.MIDDLE));
        mouse.mouseReleased(createEvent(Mouse.MIDDLE, 0, 0));
        Assert.assertFalse(mouse.hasClickedOnce(Mouse.MIDDLE));
    }

    /**
     * Test the mouse click.
     */
    @Test
    public void testClick()
    {
        final MouseAwt mouse = createMouse();

        mouse.mousePressed(createEvent(Mouse.MIDDLE, 0, 0));
        Assert.assertEquals(Mouse.MIDDLE, mouse.getClick());
        mouse.mouseReleased(createEvent(Mouse.MIDDLE, 0, 0));
        Assert.assertNotEquals(Mouse.MIDDLE, mouse.getClick());
    }

    /**
     * Test the mouse on screen.
     */
    @Test
    public void testLocation()
    {
        final MouseAwt mouse = createMouse();

        mouse.mouseMoved(createEvent(Mouse.LEFT, 0, 0));
        Assert.assertEquals(0, mouse.getX());
        Assert.assertEquals(0, mouse.getY());

        mouse.mouseMoved(createEvent(Mouse.LEFT, 10, 20));
        Assert.assertEquals(10, mouse.getX());
        Assert.assertEquals(20, mouse.getY());
    }

    /**
     * Test the mouse do click robot.
     */
    @Test
    public void testDoClick()
    {
        final MouseAwt mouse = createMouse();

        Assert.assertFalse(mouse.hasClicked(Mouse.RIGHT));
        mouse.mouseMoved(createEvent(Mouse.LEFT, 0, 0));
        mouse.doClickAt(Mouse.RIGHT, 500, 500);
        try
        {
            Assert.assertEquals(0, mouse.getOnScreenX());
            Assert.assertEquals(0, mouse.getOnScreenY());
            Assert.assertFalse(mouse.hasClicked(Mouse.RIGHT));
        }
        finally
        {
            mouse.doClickAt(Mouse.LEFT, 499, 499);
        }

        Assert.assertFalse(mouse.hasClicked(Mouse.LEFT));
        mouse.doClick(Mouse.LEFT);
        Assert.assertFalse(mouse.hasClicked(Mouse.LEFT));
        mouse.doClick(Mouse.MIDDLE);

        mouse.lock();
        mouse.lock(500, 500);
    }

    /**
     * Test the mouse move.
     */
    @Test
    public void testMouse()
    {
        final MouseAwt mouse = createMouse();

        mouse.mouseMoved(createEvent(Mouse.LEFT, 0, 0));
        mouse.mouseDragged(createEvent(0, 0, 0));
        mouse.update(1.0);
        Assert.assertEquals(0, mouse.getMoveX());
        Assert.assertEquals(0, mouse.getMoveY());
        Assert.assertTrue(mouse.hasMoved());
        Assert.assertFalse(mouse.hasMoved());

        mouse.mouseEntered(null);
        mouse.mouseExited(null);
        mouse.mouseWheelMoved(null);
        mouse.mouseClicked(null);
    }

    /**
     * Test the mouse event.
     */
    @Test
    public void testEvent()
    {
        final MouseAwt mouse = createMouse();
        final AtomicBoolean left = new AtomicBoolean(false);

        mouse.addActionPressed(Mouse.LEFT, () -> left.set(true));
        mouse.addActionPressed(Mouse.LEFT, () -> left.set(true));
        mouse.addActionReleased(Mouse.LEFT, () -> left.set(false));
        mouse.addActionReleased(Mouse.LEFT, () -> left.set(false));
        Assert.assertFalse(left.get());

        mouse.mousePressed(createEvent(Mouse.LEFT, 0, 0));
        Assert.assertTrue(left.get());

        mouse.mouseReleased(createEvent(Mouse.LEFT, 0, 0));
        Assert.assertFalse(left.get());
    }
}
