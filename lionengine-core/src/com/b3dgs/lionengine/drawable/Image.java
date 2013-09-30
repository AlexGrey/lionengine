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
package com.b3dgs.lionengine.drawable;

/**
 * It allows images loading and rendering. Images can't be resized and can't use any filters.
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * // Load
 * final Image image = Drawable.loadImage(Media.get(&quot;image.png&quot;));
 * 
 * // Render
 * image.render(g, 0, 0);
 * </pre>
 */
public interface Image
        extends Renderable
{
    /**
     * Get the surface which represents the image (java type).
     * 
     * @return The java image reference.
     */
    java.awt.Image getSurface();

    /**
     * Get instanced version of current image (faster than a clone, but shares the same surface).
     * 
     * @return The instanced image.
     */
    Image instanciate();
}
