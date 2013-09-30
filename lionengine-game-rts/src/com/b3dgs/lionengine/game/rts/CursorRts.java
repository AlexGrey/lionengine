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
package com.b3dgs.lionengine.game.rts;

import java.awt.geom.Rectangle2D;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Resolution;
import com.b3dgs.lionengine.drawable.Cursor;
import com.b3dgs.lionengine.game.Tiled;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.input.Mouse;

/**
 * This class can be used to handle easily a strategy cursor, designed to select and give order to any kind of entity.
 * The cursor can be asynchronous (compared to the mouse window).
 */
public class CursorRts
        extends Cursor
{
    /** Grid width. */
    private final int gridWidth;
    /** Grid height. */
    private final int gridHeight;
    /** Grid rectangle buffer. */
    private final Rectangle2D grid;
    /** Screen width. */
    private final int width;
    /** Screen height. */
    private final int height;
    /** Offset x. */
    private int offX;
    /** Offset y. */
    private int offY;
    /** Old location x. */
    private int oldX;
    /** Old location y. */
    private int oldY;

    /**
     * Create a new rts cursor.
     * 
     * @param source The source display.
     * @param cursor The cursor images media.
     * @param map The map reference.
     */
    public CursorRts(Resolution source, MapTile<?, ?> map, Media... cursor)
    {
        this(source, map.getTileWidth(), map.getTileHeight(), cursor);
    }

    /**
     * Create a new rts cursor.
     * 
     * @param source The source display.
     * @param cursor The cursor images media.
     * @param tileWidth The tile width.
     * @param tileHeight The tile height.
     */
    public CursorRts(Resolution source, int tileWidth, int tileHeight, Media... cursor)
    {
        super(0, 0, source.getWidth(), source.getHeight(), cursor);
        setLocation(source.getWidth() / 2, source.getHeight() / 2);
        gridWidth = tileWidth;
        gridHeight = tileHeight;
        grid = new Rectangle2D.Double();
        width = source.getWidth();
        height = source.getHeight();
    }

    /**
     * This update function is preferable to the other one, as it allows to interact with objects in the map.
     * 
     * @param extrp The extrapolation value.
     * @param camera The camera reference.
     * @param mouse The mouse reference.
     * @param async The sync mode (<code>true</code> = sync to window mouse; <code>false</code> = internal movement).
     */
    public void update(double extrp, CameraRts camera, Mouse mouse, boolean async)
    {
        oldX = super.getLocationX();
        oldY = super.getLocationY() - camera.getViewY();
        update(extrp, mouse, async);
        setArea(0, 0, width, height);
        offX = camera.getLocationIntX();
        offY = camera.getLocationIntY() - camera.getViewY() * 2 - 1;
    }

    /**
     * Get a rectangle describing a grid (placed on the cursor, depending of map tile size).
     * 
     * @param size The size (in tile square).
     * @return rectangle The rectangle reference.
     */
    public Rectangle2D getGrid(int size)
    {
        final int x = getLocationX() / gridWidth * gridWidth;
        final int y = getLocationY() / gridHeight * gridHeight;
        grid.setRect(x, y, gridWidth * size, gridHeight * size);
        return grid;
    }

    /**
     * Get cursor location x on screen (not sync to any camera).
     * 
     * @return The cursor location x on screen.
     */
    public int getScreenX()
    {
        return super.getLocationX();
    }

    /**
     * Get cursor location y on screen (not sync to any camera).
     * 
     * @return The cursor location y on screen.
     */
    public int getScreenY()
    {
        return super.getLocationY();
    }

    /**
     * Get the horizontal tile pointed by the cursor.
     * 
     * @return The horizontal tile pointed by the cursor.
     */
    public int getLocationInTileX()
    {
        return (super.getLocationX() + offX) / gridWidth;
    }

    /**
     * Get the vertical tile pointed by the cursor.
     * 
     * @return The vertical tile pointed by the cursor.
     */
    public int getLocationInTileY()
    {
        return (height - super.getLocationY() + offY) / gridHeight;
    }

    /**
     * Get cursor horizontal move.
     * 
     * @return The horizontal move.
     */
    public int getMoveX()
    {
        return super.getLocationX() - oldX;
    }

    /**
     * Get cursor vertical move.
     * 
     * @return The vertical move.
     */
    public int getMoveY()
    {
        return super.getLocationY() - oldY;
    }

    /**
     * Get the grid width.
     * 
     * @return The grid width.
     */
    public int getGridWidth()
    {
        return gridWidth;
    }

    /**
     * Get the grid height.
     * 
     * @return The grid height.
     */
    public int getGridHeight()
    {
        return gridHeight;
    }

    /**
     * Check if the cursor can be over the entity, depending of the camera view.
     * 
     * @param tiled The tiled reference.
     * @param camera The camera reference.
     * @return <code>true</code> if can click over, <code>false</code> else.
     */
    public boolean isOver(Tiled tiled, CameraRts camera)
    {
        return getScreenX() >= camera.getViewX() && getScreenX() < camera.getViewX() + camera.getViewWidth()
                && getScreenY() >= camera.getViewY() && getScreenY() < camera.getViewY() + camera.getViewHeight()
                && getLocationInTileX() >= tiled.getLocationInTileX()
                && getLocationInTileX() < tiled.getLocationInTileX() + tiled.getWidthInTile()
                && getLocationInTileY() >= tiled.getLocationInTileY()
                && getLocationInTileY() < tiled.getLocationInTileY() + tiled.getHeightInTile();
    }

    /*
     * Cursor
     */

    /**
     * Get cursor location x on map (sync to camera).
     * 
     * @return The cursor on map location x.
     */
    @Override
    public int getLocationX()
    {
        return super.getLocationX() + offX;
    }

    /**
     * Get cursor location y on map (sync to camera).
     * 
     * @return The cursor on map location y.
     */
    @Override
    public int getLocationY()
    {
        return height - super.getLocationY() + offY;
    }
}
