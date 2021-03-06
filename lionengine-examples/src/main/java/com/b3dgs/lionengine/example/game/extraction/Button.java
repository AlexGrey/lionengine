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

import java.util.concurrent.atomic.AtomicReference;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.core.awt.Mouse;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.Image;
import com.b3dgs.lionengine.game.Cursor;
import com.b3dgs.lionengine.game.feature.Featurable;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.Service;
import com.b3dgs.lionengine.game.feature.SetupSurface;
import com.b3dgs.lionengine.game.feature.actionable.Actionable;
import com.b3dgs.lionengine.game.feature.actionable.ActionableModel;
import com.b3dgs.lionengine.game.feature.assignable.Assignable;
import com.b3dgs.lionengine.game.feature.assignable.AssignableModel;
import com.b3dgs.lionengine.game.feature.displayable.DisplayableModel;
import com.b3dgs.lionengine.game.feature.extractable.Extractable;
import com.b3dgs.lionengine.game.feature.extractable.Extractor;
import com.b3dgs.lionengine.game.feature.layerable.LayerableModel;
import com.b3dgs.lionengine.game.feature.refreshable.RefreshableModel;
import com.b3dgs.lionengine.game.handler.Handler;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.pathfinding.MapTilePath;
import com.b3dgs.lionengine.graphic.Text;

/**
 * Resources button action.
 */
class Button extends FeaturableModel
{
    /** Extract media. */
    public static final Media EXTRACT = Medias.create("Extract.xml");
    /** Carry media. */
    public static final Media CARRY = Medias.create("Carry.xml");

    @Service private Text text;
    @Service private Cursor cursor;
    @Service private Handler handler;
    @Service private MapTile map;

    /**
     * Create build button action.
     * 
     * @param setup The setup reference.
     */
    public Button(SetupSurface setup)
    {
        super();

        addFeature(new LayerableModel(3));

        final Assignable assignable = addFeatureAndGet(new AssignableModel());
        assignable.setClickAssign(Mouse.LEFT);

        final Actionable actionable = addFeatureAndGet(new ActionableModel(setup));
        actionable.setClickAction(Mouse.LEFT);
        final AtomicReference<Updatable> state = new AtomicReference<>(actionable);

        actionable.setAction(() ->
        {
            cursor.setSurfaceId(1);
            state.set(assignable);
        });

        assignable.setAssign(() ->
        {
            final int tx = map.getInTileX(cursor);
            final int ty = map.getInTileY(cursor);

            final MapTilePath mapPath = map.getFeature(MapTilePath.class);
            for (final Extractor extractor : handler.get(Extractor.class))
            {
                for (final Integer id : mapPath.getObjectsId(tx, ty))
                {
                    final Featurable featurable = handler.get(id);
                    if (featurable.hasFeature(Extractable.class))
                    {
                        extractor.setResource(featurable.getFeature(Extractable.class));
                        extractor.startExtraction();
                    }
                }
            }
            cursor.setSurfaceId(0);
            state.set(actionable);
        });

        addFeature(new RefreshableModel(extrp ->
        {
            if (actionable.isOver())
            {
                text.setText(actionable.getDescription());
            }
            state.get().update(extrp);
        }));

        final Image image = Drawable.loadImage(setup.getSurface());
        image.setLocation(actionable.getButton().getX(), actionable.getButton().getY());

        addFeature(new DisplayableModel(image::render));
    }
}
