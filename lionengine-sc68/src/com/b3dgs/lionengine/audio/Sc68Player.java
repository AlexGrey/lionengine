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
package com.b3dgs.lionengine.audio;

import java.io.File;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.Media;

/**
 * SC68 player implementation.
 */
final class Sc68Player
        implements Sc68
{
    /** Binding reference. */
    private final Sc68Binding binding;

    /**
     * Constructor.
     * 
     * @param binding The binding reference.
     */
    Sc68Player(Sc68Binding binding)
    {
        Check.notNull(binding, "SC68 binding must not be null !");
        this.binding = binding;
    }

    /*
     * Sc68
     */

    @Override
    public void play(Media media)
    {
        Check.notNull(media);
        final File music = Media.getTempFile(media, true, false);
        binding.SC68Play(music.getPath());
    }

    @Override
    public void setVolume(int volume)
    {
        Check.argument(volume >= 0 && volume <= 100, "Wrong volume value !");
        binding.SC68Volume(volume);
    }

    @Override
    public void pause()
    {
        binding.SC68Pause();
    }

    @Override
    public void resume()
    {
        binding.SC68UnPause();
    }

    @Override
    public void stop()
    {
        binding.SC68Stop();
    }

    @Override
    public void free()
    {
        binding.SC68Free();
    }

    @Override
    public int seek()
    {
        return binding.SC68Seek();
    }
}
