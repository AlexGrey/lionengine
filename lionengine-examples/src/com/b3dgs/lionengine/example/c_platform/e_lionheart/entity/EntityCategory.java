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
package com.b3dgs.lionengine.example.c_platform.e_lionheart.entity;

import java.util.Locale;

/**
 * List of entity categories.
 */
public enum EntityCategory
{
    /** Item (can be taken). */
    ITEM("items"),
    /** Monster (can be destroyed and attack the player). */
    MONSTER("monsters"),
    /** Scenery (other objects that are required to complete a level). */
    SCENERY("sceneries"),
    /** Valdyn (player). */
    PLAYER("players");

    /** Folder name. */
    private final String folder;
    /** Count number. */
    private int count;

    /**
     * Constructor.
     * 
     * @param folder The folder name.
     */
    private EntityCategory(String folder)
    {
        this.folder = folder;
    }

    /**
     * Get the folder name.
     * 
     * @return The folder name.
     */
    public String getFolder()
    {
        return folder;
    }

    /**
     * Get the name as a path (lower case).
     * 
     * @return The name.
     */
    public String asPathName()
    {
        return name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Get the count number.
     * 
     * @return The count number.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Increase the count number.
     */
    void increase()
    {
        count++;
    }

    /**
     * Get the title name (first letter as upper).
     * 
     * @return The title name.
     */
    @Override
    public String toString()
    {
        final String string = asPathName();
        return Character.toString(string.charAt(0)).toUpperCase(Locale.ENGLISH) + string.substring(1);
    }
}
