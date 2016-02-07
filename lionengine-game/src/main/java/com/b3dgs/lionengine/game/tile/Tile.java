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
package com.b3dgs.lionengine.game.tile;

import com.b3dgs.lionengine.Localizable;
import com.b3dgs.lionengine.Surface;
import com.b3dgs.lionengine.game.Featurable;

/**
 * Tile representation with the following data:
 * <ul>
 * <li><code>size</code> : tile size</li>
 * <li><code>sheet</code> : tile sheet number</li>
 * <li><code>number</code> : tile number inside tilesheet</li>
 * <li><code>x and y</code> : real location on map</li>
 * </ul>
 * <p>
 * A tile represents a surface, localized on a {@link com.b3dgs.lionengine.game.map.MapTile}.
 * </p>
 * <p>
 * Services can be extended by using the {@link TileFeature} layer.
 * </p>
 * 
 * @see com.b3dgs.lionengine.game.map.MapTile
 */
public interface Tile extends Surface, Localizable, Tiled, Featurable<TileFeature>
{
    /**
     * Get sheet number.
     * 
     * @return The sheet number.
     */
    Integer getSheet();

    /**
     * Get tile index number.
     * 
     * @return The tile index number.
     */
    int getNumber();
}
