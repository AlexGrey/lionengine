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
package com.b3dgs.lionengine.tutorials.mario.d;

import java.io.IOException;

import com.b3dgs.lionengine.core.Context;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.core.awt.Keyboard;
import com.b3dgs.lionengine.game.camera.CameraTracker;
import com.b3dgs.lionengine.game.collision.object.ComponentCollision;
import com.b3dgs.lionengine.game.collision.tile.MapTileCollision;
import com.b3dgs.lionengine.game.collision.tile.MapTileCollisionModel;
import com.b3dgs.lionengine.game.handler.WorldGame;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.MapTileGame;
import com.b3dgs.lionengine.game.map.feature.group.MapTileGroup;
import com.b3dgs.lionengine.game.map.feature.group.MapTileGroupModel;
import com.b3dgs.lionengine.game.map.feature.persister.MapTilePersister;
import com.b3dgs.lionengine.game.map.feature.persister.MapTilePersisterModel;
import com.b3dgs.lionengine.game.map.feature.viewer.MapTileViewerModel;
import com.b3dgs.lionengine.graphic.ColorRgba;
import com.b3dgs.lionengine.graphic.Graphic;
import com.b3dgs.lionengine.stream.FileReading;
import com.b3dgs.lionengine.stream.FileWriting;

/**
 * World implementation.
 */
class World extends WorldGame
{
    private static final ColorRgba BACKGROUND_COLOR = new ColorRgba(107, 136, 255);

    private final MapTile map = services.create(MapTileGame.class);

    /**
     * Constructor.
     * 
     * @param context The context reference.
     */
    public World(Context context)
    {
        super(context);

        services.add(getInputDevice(Keyboard.class));
        services.add(Integer.valueOf(source.getRate()));

        camera.setIntervals(16, 0);

        map.addFeature(new MapTilePersisterModel());
        map.addFeature(new MapTileViewerModel());
        map.addFeature(new MapTileGroupModel());
        map.addFeature(new MapTileCollisionModel());

        handler.addComponent(new ComponentCollision());
    }

    @Override
    public void render(Graphic g)
    {
        fill(g, BACKGROUND_COLOR);
        super.render(g);
    }

    @Override
    protected void saving(FileWriting file) throws IOException
    {
        map.getFeature(MapTilePersister.class).save(file);
    }

    @Override
    protected void loading(FileReading file) throws IOException
    {
        handler.add(map);

        map.getFeature(MapTilePersister.class).load(file);
        map.getFeature(MapTileGroup.class).loadGroups(Medias.create("map", "groups.xml"));
        map.getFeature(MapTileCollision.class).loadCollisions(Medias.create("map", "formulas.xml"),
                                                              Medias.create("map", "collisions.xml"));

        final Entity mario = factory.create(Entity.MARIO);
        handler.add(mario);

        final CameraTracker tracker = camera.addFeatureAndGet(new CameraTracker());
        camera.setLimits(map);
        tracker.track(mario);
        handler.add(camera);

        for (int i = 0; i < 20; i++)
        {
            final Entity goomba = factory.create(Entity.GOOMBA);
            goomba.getFeature(GoombaUpdater.class).respawn(500 + i * 50);
            handler.add(goomba);
        }
    }
}
