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
package com.b3dgs.lionengine.game.platform.background;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.SpriteTiled;
import com.b3dgs.lionengine.utility.UtilityMath;

/**
 * This class handle clouds effect.
 */
public abstract class CloudsPlatform
        implements BackgroundComponent
{
    /** Clouds surface. */
    protected final SpriteTiled sprite;
    /** Cloud element. */
    private final BackgroundElement data;
    /** Clouds number. */
    private final int cloudsNumber;
    /** Clouds wide. */
    private final int w;
    /** Clouds location x. */
    private final double[] x;
    /** Clouds location y. */
    private final double[] y;
    /** Clouds speed. */
    private final double[] speed;
    /** Vertical offset. */
    private final int decY;

    /**
     * Create a new cloud.
     * 
     * @param media The clouds image media.
     * @param cloudWidth The image width.
     * @param cloudHeight The image height.
     * @param screenWidth The screen height.
     * @param decY The vertical offset.
     * @param alpha <code>true</code> if clouds surface uses alpha, <code>false</code> else.
     */
    public CloudsPlatform(Media media, int cloudWidth, int cloudHeight, int screenWidth, int decY, boolean alpha)
    {
        this.decY = decY;

        // Load surface
        sprite = Drawable.loadSpriteTiled(media, cloudWidth, cloudHeight);
        sprite.load(alpha);
        cloudsNumber = sprite.getTilesNumber();
        data = new BackgroundElement(0, 0, sprite);

        final int wi = (int) Math.ceil(screenWidth / (double) sprite.getWidthOriginal()) + 1;
        w = wi;

        // Data arrays
        x = new double[cloudsNumber];
        y = new double[cloudsNumber];
        speed = new double[cloudsNumber];

        // Positions
        for (int i = 0; i < cloudsNumber; i++)
        {
            x[i] = 0;
        }
    }

    /**
     * Set cloud line height (usually, line 0 is higher than last line).
     * 
     * @param line The cloud line (0 = top, last = bottom).
     * @param y The cloud height.
     */
    public void setY(int line, int y)
    {
        this.y[line] = y;
    }

    /**
     * Set cloud line speed (usually, line 0 is faster than last line).
     * 
     * @param line The cloud line (0 = top, last = bottom).
     * @param speed The cloud speed.
     */
    public void setSpeed(int line, double speed)
    {
        this.speed[line] = speed;
    }

    /*
     * BackgroundComponent
     */

    @Override
    public void update(double extrp, int x, int y, double speed)
    {
        data.setOffsetY(y);
        for (int i = 0; i < cloudsNumber; i++)
        {
            this.x[i] += this.speed[i] * extrp;
            this.x[i] = UtilityMath.wrapDouble(this.x[i], 0.0, sprite.getWidth());
        }
    }

    @Override
    public void render(Graphic g)
    {
        final double lx = data.getOffsetX() + data.getMainX() - sprite.getWidth();
        final double ly = data.getOffsetY() + data.getMainY();
        final int sw = sprite.getWidth();

        for (int i = 0; i < cloudsNumber; i++)
        {
            // w number of renders used to fill screen
            for (int j = 0; j < w; j++)
            {
                sprite.render(g, i, (int) (lx + x[i] + sw * j), (int) (ly + y[i] + decY));
            }
        }
    }
}
