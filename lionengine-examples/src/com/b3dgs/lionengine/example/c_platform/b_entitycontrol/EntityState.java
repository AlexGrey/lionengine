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
package com.b3dgs.lionengine.example.c_platform.b_entitycontrol;

import java.util.Locale;

/**
 * List of entity states.
 */
enum EntityState
{
    /** Idle state. */
    IDLE,
    /** Walk state. */
    WALK,
    /** turn state. */
    TURN,
    /** Jump state. */
    JUMP;

    /** Animation name. */
    private final String animationName;

    /**
     * Constructor.
     */
    private EntityState()
    {
        animationName = name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Get the animation name.
     * 
     * @return The animation name.
     */
    public String getAnimationName()
    {
        return animationName;
    }
}
