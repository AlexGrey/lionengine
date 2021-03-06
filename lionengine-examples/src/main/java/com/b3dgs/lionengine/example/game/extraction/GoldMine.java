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

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.Sprite;
import com.b3dgs.lionengine.game.feature.FeaturableModel;
import com.b3dgs.lionengine.game.feature.Service;
import com.b3dgs.lionengine.game.feature.Services;
import com.b3dgs.lionengine.game.feature.SetupSurface;
import com.b3dgs.lionengine.game.feature.displayable.DisplayableModel;
import com.b3dgs.lionengine.game.feature.extractable.Extractable;
import com.b3dgs.lionengine.game.feature.extractable.ExtractableModel;
import com.b3dgs.lionengine.game.feature.layerable.LayerableModel;
import com.b3dgs.lionengine.game.feature.refreshable.RefreshableModel;
import com.b3dgs.lionengine.game.feature.transformable.Transformable;
import com.b3dgs.lionengine.game.feature.transformable.TransformableModel;
import com.b3dgs.lionengine.game.pathfinding.Pathfindable;
import com.b3dgs.lionengine.game.pathfinding.PathfindableModel;
import com.b3dgs.lionengine.graphic.Viewer;

/**
 * Building implementation.
 */
class GoldMine extends FeaturableModel
{
    /** Gold mine media reference. */
    public static final Media GOLD_MINE = Medias.create("GoldMine.xml");

    private final Pathfindable pathfindable;

    @Service private Viewer viewer;

    /**
     * Create a building.
     * 
     * @param setup The setup reference.
     */
    public GoldMine(SetupSurface setup)
    {
        super();

        final Transformable transformable = addFeatureAndGet(new TransformableModel(setup));
        pathfindable = addFeatureAndGet(new PathfindableModel(setup));

        final Sprite surface = Drawable.loadSprite(setup.getSurface());
        surface.setOrigin(Origin.TOP_LEFT);

        final Extractable extractable = addFeatureAndGet(new ExtractableModel());
        extractable.setResourcesQuantity(100);

        addFeature(new LayerableModel(1));
        addFeature(new RefreshableModel(extrp -> surface.setLocation(viewer, transformable)));
        addFeature(new DisplayableModel(surface::render));
    }

    @Override
    public void prepareFeatures(Services services)
    {
        super.prepareFeatures(services);

        pathfindable.setLocation(21, 14);
    }
}
