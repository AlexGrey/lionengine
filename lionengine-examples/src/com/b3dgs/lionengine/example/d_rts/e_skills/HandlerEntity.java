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
package com.b3dgs.lionengine.example.d_rts.e_skills;

import java.awt.Color;
import java.util.Set;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.example.d_rts.e_skills.entity.Entity;
import com.b3dgs.lionengine.game.TextGame;
import com.b3dgs.lionengine.game.rts.CameraRts;
import com.b3dgs.lionengine.game.rts.CursorRts;
import com.b3dgs.lionengine.game.rts.HandlerEntityRts;

/**
 * Handler implementation, containing all entities.
 */
public final class HandlerEntity
        extends HandlerEntityRts<ResourceType, Tile, Entity, ControlPanel>
{
    /** Cursor reference. */
    private final Cursor cursor;

    /**
     * Constructor.
     * 
     * @param camera The camera reference.
     * @param cursor The cursor reference.
     * @param controlPanel The panel reference.
     * @param map The map reference.
     * @param text The text reference.
     */
    HandlerEntity(CameraRts camera, Cursor cursor, ControlPanel controlPanel, Map map, TextGame text)
    {
        super(camera, cursor, controlPanel, map);
        this.cursor = cursor;
    }

    /*
     * HandlerEntityRts
     */

    @Override
    public void update(double extrp)
    {
        if (cursor.getType() == CursorType.WEN)
        {
            cursor.setType(CursorType.POINTER);
        }
        super.update(extrp);
    }

    @Override
    protected void updatingEntity(Entity entity, CursorRts cursor, CameraRts camera)
    {
        // Adapt cursor
        if (cursor.getClick() == 0 && this.cursor.getType() == CursorType.POINTER && entity.isOver()
                && cursor.isOver(entity, camera))
        {
            this.cursor.setType(CursorType.WEN);
        }
    }

    @Override
    protected void renderingEntity(Graphic g, Entity entity, CameraRts camera, CursorRts cursor)
    {
        if (cursor.getClick() == 0 && entity.isOver() && !panel.canClick(cursor) || entity.isSelected())
        {
            super.renderingEntity(g, entity, camera, cursor);
        }
    }

    @Override
    protected void notifyUpdatedSelection(Set<Entity> selection)
    {
        // Nothing to do
    }

    @Override
    protected Color getEntityColorOver(Entity entity)
    {
        return Color.GRAY;
    }

    @Override
    protected Color getEntityColorSelection(Entity entity)
    {
        return Color.GREEN;
    }
}
