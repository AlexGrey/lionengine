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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.Filter;
import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.ImageInfo;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.utility.UtilityImage;

/**
 * Sprite implementation.
 */
class SpriteImpl
        implements Sprite
{
    /** Sprite original width. */
    protected final int widthOriginal;
    /** Sprite original height. */
    protected final int heightOriginal;
    /** Sprite surface. */
    protected BufferedImage surface;
    /** Sprite width. */
    protected int width;
    /** Sprite height. */
    protected int height;
    /** Sprite file name. */
    private final Media media;
    /** Sprite raw data (used for alpha). */
    private int[][] rgb;
    /** First alpha. */
    private boolean firstAlpha;

    /**
     * Create a new sprite.
     * 
     * @param media The sprite media.
     */
    SpriteImpl(Media media)
    {
        this.media = media;
        final ImageInfo info = ImageInfo.get(media);
        widthOriginal = info.getWidth();
        heightOriginal = info.getHeight();
        width = widthOriginal;
        height = heightOriginal;
        rgb = null;
    }

    /**
     * Create a new sprite from an existing surface (share).
     * 
     * @param surface The surface to share.
     */
    SpriteImpl(BufferedImage surface)
    {
        this.surface = surface;
        media = null;
        widthOriginal = surface.getWidth();
        heightOriginal = surface.getHeight();
        width = widthOriginal;
        height = heightOriginal;
        rgb = null;
    }

    /*
     * Sprite
     */

    @Override
    public void load(boolean alpha)
    {
        surface = UtilityImage.getBufferedImage(media, alpha);
    }

    @Override
    public void scale(int percent)
    {
        stretch(percent, percent);
    }

    @Override
    public void stretch(int widthPercent, int heightPercent)
    {
        Check.argument(widthPercent > 0, "Width percent must be strictly positive !");
        Check.argument(heightPercent > 0, "Height percent must be strictly positive !");

        if (widthPercent != 100 || heightPercent != 100)
        {
            final int newWidth = getWidthOriginal() * widthPercent / 100;
            final int newHeight = getHeightOriginal() * heightPercent / 100;
            width = newWidth;
            height = newHeight;
            surface = UtilityImage.resize(surface, newWidth, newHeight);
        }
    }

    @Override
    public void rotate(int angle)
    {
        surface = UtilityImage.rotate(surface, angle);
    }

    @Override
    public void flipHorizontal()
    {
        surface = UtilityImage.flipHorizontal(surface);
    }

    @Override
    public void flipVertical()
    {
        surface = UtilityImage.flipVertical(surface);
    }

    @Override
    public void filter(Filter filter)
    {
        surface = UtilityImage.applyFilter(surface, filter);
    }

    @Override
    public void setTransparency(Color mask)
    {
        surface = UtilityImage.applyMask(surface, mask);
    }

    @Override
    public void setAlpha(int alpha)
    {
        Check.argument(alpha >= 0 && alpha <= 255, "Alpha must be >= 0 and <= 255 !");
        if (rgb == null)
        {
            rgb = new int[width][height];
            firstAlpha = true;
        }
        for (int cx = 0; cx < surface.getWidth(); cx++)
        {
            for (int cy = 0; cy < surface.getHeight(); cy++)
            {
                if (firstAlpha)
                {
                    rgb[cx][cy] = surface.getRGB(cx, cy);
                }
                final int alphaDec = 24;
                final int alphaKey = 0x00ffffff;
                final int mc = alpha << alphaDec | alphaKey;
                surface.setRGB(cx, cy, rgb[cx][cy] & mc);
            }
        }
        firstAlpha = false;
    }

    @Override
    public void render(Graphic g, int x, int y)
    {
        g.drawImage(surface, x, y);
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public int getWidthOriginal()
    {
        return widthOriginal;
    }

    @Override
    public int getHeightOriginal()
    {
        return heightOriginal;
    }

    @Override
    public BufferedImage getSurface()
    {
        return surface;
    }

    @Override
    public Sprite instanciate()
    {
        return new SpriteImpl(surface);
    }

    /*
     * Object
     */

    @Override
    public boolean equals(Object object)
    {
        if (object == this)
        {
            return true;
        }
        if (object instanceof Sprite)
        {
            final Sprite sprite = (Sprite) object;

            final boolean sameSurface = sprite.getSurface() == getSurface();
            final boolean sameWidth = sprite.getWidth() == getWidth();
            final boolean sameHeight = sprite.getHeight() == getHeight();

            return sameSurface && sameWidth && sameHeight;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + heightOriginal;
        result = prime * result + (media == null ? 0 : media.hashCode());
        result = prime * result + Arrays.hashCode(rgb);
        result = prime * result + (surface == null ? 0 : surface.hashCode());
        result = prime * result + width;
        result = prime * result + widthOriginal;
        return result;
    }
}
