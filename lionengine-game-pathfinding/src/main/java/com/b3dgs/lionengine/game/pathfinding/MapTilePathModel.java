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
package com.b3dgs.lionengine.game.pathfinding;

import com.b3dgs.lionengine.UtilMath;
import com.b3dgs.lionengine.game.CoordTile;
import com.b3dgs.lionengine.game.Tiled;
import com.b3dgs.lionengine.game.collision.CollisionFormula;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.Tile;
import com.b3dgs.lionengine.game.map.TileCollision;

/**
 * Abstract representation of a path based map, used for pathfinding.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class MapTilePathModel
        implements MapTilePath
{
    /** Map reference. */
    private final MapTile map;
    /** Reference object id array. */
    private Integer[][] ref;

    /**
     * Constructor base.
     * 
     * @param map The map tile reference.
     */
    public MapTilePathModel(MapTile map)
    {
        this.map = map;
        ref = null;
    }

    /**
     * Search a free area from this area.
     * 
     * @param entity The entity to search around.
     * @param radius The search size.
     * @return The free place found.
     */
    public CoordTile getFreeTileAround(Tiled entity, int radius)
    {
        return getFreeTileAround(entity.getLocationInTileX(), entity.getLocationInTileY(), radius);
    }

    /**
     * Get the closest tile location around the area. The returned tile is corresponding to the required collision.
     * 
     * @param from The tiled reference.
     * @param to The tiled reference.
     * @param collision The collision to search
     * @param radius The search size.
     * @return The closest location found.
     */
    public CoordTile getClosestTile(Tiled from, Tiled to, CollisionFormula collision, int radius)
    {
        final int sx = to.getLocationInTileX();
        final int sy = to.getLocationInTileY();

        final int fx = to.getLocationInTileX();
        final int fy = to.getLocationInTileY();
        final int fw = from.getWidthInTile();
        final int fh = from.getHeightInTile();
        int closestX = 0;
        int closestY = 0;
        int dist = Integer.MAX_VALUE;
        int size = 1;
        boolean found = false;
        while (!found)
        {
            for (int x = sx - size; x <= sx + size; x++)
            {
                for (int y = sy - size; y <= sy + size; y++)
                {
                    final Tile tile = map.getTile(x, y);
                    final TileCollision tileCollision = tile.getFeature(TileCollision.class);
                    if (tileCollision.getCollisionFormulas().contains(collision))
                    {
                        final int d = UtilMath.getDistance(fx, fy, fw, fh, x, y, 1, 1);
                        if (d < dist)
                        {
                            dist = d;
                            closestX = x;
                            closestY = y;
                            found = true;
                        }
                    }
                }
            }
            size++;
            if (size >= radius)
            {
                return null;
            }
        }
        return new CoordTile(closestX, closestY);
    }

    /**
     * Get the closest unused location around the area. The returned tile is not blocking, nor used by an entity.
     * 
     * @param sx The horizontal location.
     * @param sy The vertical location.
     * @param sw The source location width.
     * @param sh The source location height.
     * @param radius The search size.
     * @param dx The horizontal destination location.
     * @param dy The vertical destination location.
     * @param dw The destination location width.
     * @param dh The destination location height.
     * @return The closest location found.
     */
    private CoordTile getClosestAvailableTile(int sx, int sy, int sw, int sh, int radius, int dx, int dy, int dw, int dh)
    {
        int closestX = 0;
        int closestY = 0;
        int dist = Integer.MAX_VALUE;
        int size = 1;
        boolean found = false;
        while (!found)
        {
            for (int x = sx - size; x <= sx + size; x++)
            {
                for (int y = sy - size; y <= sy + size; y++)
                {
                    if (isAreaAvailable(x, y, sw, sh, 0))
                    {
                        final int d = UtilMath.getDistance(x, y, sw, sh, dx, dy, dw, dh);
                        if (d < dist)
                        {
                            dist = d;
                            closestX = x;
                            closestY = y;
                            found = true;
                        }
                    }
                }
            }
            size++;
            if (size >= radius)
            {
                return null;
            }
        }
        return new CoordTile(closestX, closestY);
    }

    @Override
    public void create(int widthInTile, int heightInTile)
    {
        ref = new Integer[heightInTile][widthInTile];
        final Integer value = Integer.valueOf(0);

        for (int v = 0; v < heightInTile; v++)
        {
            for (int h = 0; h < widthInTile; h++)
            {
                ref[v][h] = value;
            }
        }
    }

    @Override
    public Tile getTile(Tiled tiled)
    {
        return map.getTile(tiled.getLocationInTileX(), tiled.getLocationInTileY());
    }

    @Override
    public boolean isBlocked(Pathfindable mover, int dx, int dy, boolean ignoreRef)
    {
        if (dy < 0 || dx < 0 || dy >= map.getHeightInTile() || dx >= map.getWidthInTile())
        {
            return true;
        }
        if (mover.isIgnoredId(getRef(dx, dy)))
        {
            return false;
        }
        final Tile tile = map.getTile(dx, dy);
        final TilePath tilePath = tile.getFeature(TilePath.class);
        if (ignoreRef)
        {
            return tilePath.isBlocking();
        }
        return tilePath.isBlocking() || getRef(dx, dy).intValue() > 0;
    }

    @Override
    public double getCost(Pathfindable mover, int sx, int sy, int tx, int ty)
    {
        return 1;
    }

    @Override
    public void setRef(int tx, int ty, Integer id)
    {
        ref[ty][tx] = id;
    }

    @Override
    public Integer getRef(int tx, int ty)
    {
        return ref[ty][tx];
    }

    @Override
    public CoordTile getFreeTileAround(int tx, int ty, int radius)
    {
        int size = 0;
        boolean search = true;
        while (search)
        {
            for (int x = tx - size; x <= tx + size; x++)
            {
                for (int y = ty - size; y <= ty + size; y++)
                {
                    if (isAreaAvailable(x, y, 1, 1, 0))
                    {
                        return new CoordTile(x, y);
                    }
                }
            }
            size++;
            if (size > radius)
            {
                search = false;
            }
        }
        return null;
    }

    @Override
    public CoordTile getClosestAvailableTile(Tiled from, int radius, Tiled to)
    {
        return getClosestAvailableTile(from.getLocationInTileX(), from.getLocationInTileY(), from.getWidthInTile(),
                from.getHeightInTile(), radius, to.getLocationInTileX(), to.getLocationInTileY(), to.getWidthInTile(),
                to.getHeightInTile());
    }

    @Override
    public CoordTile getClosestAvailableTile(int sx, int sy, int radius, int dx, int dy)
    {
        return getClosestAvailableTile(sx, sy, 1, 1, radius, dx, dy, 1, 1);
    }

    @Override
    public boolean isAreaAvailable(int tx, int ty, int w, int h, int ignoreRef)
    {
        for (int y = ty; y < ty + h; y++)
        {
            for (int x = tx; x < tx + w; x++)
            {
                try
                {
                    final int r = getRef(x, y).intValue();
                    final Tile tile = map.getTile(x, y);
                    final TilePath tilePath = tile.getFeature(TilePath.class);
                    if (r > 0 && r != ignoreRef || tilePath.isBlocking())
                    {
                        return false;
                    }
                }
                catch (final ArrayIndexOutOfBoundsException exception)
                {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public MapTile getMap()
    {
        return map;
    }
}