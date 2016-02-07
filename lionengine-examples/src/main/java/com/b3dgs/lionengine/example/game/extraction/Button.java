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
package com.b3dgs.lionengine.example.game.extraction;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Renderable;
import com.b3dgs.lionengine.Text;
import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.core.awt.Mouse;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.Image;
import com.b3dgs.lionengine.game.Cursor;
import com.b3dgs.lionengine.game.layer.Layerable;
import com.b3dgs.lionengine.game.layer.LayerableModel;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.object.Handler;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.SetupSurface;
import com.b3dgs.lionengine.game.object.trait.actionable.Action;
import com.b3dgs.lionengine.game.object.trait.actionable.Actionable;
import com.b3dgs.lionengine.game.object.trait.actionable.ActionableModel;
import com.b3dgs.lionengine.game.object.trait.assignable.Assign;
import com.b3dgs.lionengine.game.object.trait.assignable.Assignable;
import com.b3dgs.lionengine.game.object.trait.assignable.AssignableModel;
import com.b3dgs.lionengine.game.object.trait.extractable.Extractable;
import com.b3dgs.lionengine.game.object.trait.extractable.Extractor;
import com.b3dgs.lionengine.game.pathfinding.MapTilePath;

/**
 * Resources button action.
 */
class Button extends ObjectGame implements Action, Assign, Updatable, Renderable
{
    /** Extract media. */
    public static final Media EXTRACT = Medias.create("Extract.xml");
    /** Carry media. */
    public static final Media CARRY = Medias.create("Carry.xml");

    /** Actionable model. */
    private final Actionable actionable = addTrait(new ActionableModel());
    /** Assignable model. */
    private final Assignable assignable = addTrait(new AssignableModel());
    /** Layerable model. */
    private final Layerable layerable = addTrait(new LayerableModel());
    /** Button image. */
    private final Image image;
    /** Text reference. */
    private final Text text;
    /** Cursor reference. */
    private final Cursor cursor;
    /** Handler reference. */
    private final Handler handler;
    /** Map reference. */
    private final MapTile map;
    /** Map path reference. */
    private final MapTilePath mapPath;
    /** Current action state. */
    private Updatable state;

    /**
     * Create build button action.
     * 
     * @param setup The setup reference.
     * @param services The services reference.
     */
    public Button(SetupSurface setup, Services services)
    {
        super(setup, services);
        image = Drawable.loadImage(setup.getSurface());

        text = services.get(Text.class);
        cursor = services.get(Cursor.class);
        handler = services.get(Handler.class);
        map = services.get(MapTile.class);
        mapPath = services.get(MapTilePath.class);

        actionable.setClickAction(Mouse.LEFT);
        assignable.setClickAssign(Mouse.LEFT);
    }

    @Override
    protected void onPrepared()
    {
        image.setLocation(actionable.getButton().getX(), actionable.getButton().getY());

        actionable.setAction(this);
        assignable.setAssign(this);

        layerable.setLayer(Integer.valueOf(3));
        state = actionable;
    }

    @Override
    public void execute()
    {
        cursor.setSurfaceId(1);
        state = assignable;
    }

    @Override
    public void assign()
    {
        final int tx = map.getInTileX(cursor);
        final int ty = map.getInTileY(cursor);
        for (final Extractor extractor : handler.get(Extractor.class))
        {
            for (final Integer id : mapPath.getObjectsId(tx, ty))
            {
                final ObjectGame object = handler.get(id);
                if (object.hasTrait(Extractable.class))
                {
                    extractor.setResource(object.getTrait(Extractable.class));
                    extractor.startExtraction();
                }
            }
        }
        cursor.setSurfaceId(0);
        state = actionable;
    }

    @Override
    public void update(double extrp)
    {
        if (actionable.isOver())
        {
            text.setText(actionable.getDescription());
        }
        state.update(extrp);
    }

    @Override
    public void render(Graphic g)
    {
        image.render(g);
    }
}
