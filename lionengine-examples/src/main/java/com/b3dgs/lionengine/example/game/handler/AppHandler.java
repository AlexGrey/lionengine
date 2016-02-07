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
package com.b3dgs.lionengine.example.game.handler;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.core.Engine;
import com.b3dgs.lionengine.core.Graphics;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.core.Version;
import com.b3dgs.lionengine.core.awt.EngineAwt;
import com.b3dgs.lionengine.game.object.ComponentRenderer;
import com.b3dgs.lionengine.game.object.ComponentUpdater;
import com.b3dgs.lionengine.game.object.Handler;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.Setup;

/**
 * Main class.
 * 
 * @see com.b3dgs.lionengine.example.core.minimal
 */
public class AppHandler
{
    /**
     * Main.
     * 
     * @param args The arguments.
     */
    public static void main(String[] args)
    {
        EngineAwt.start("Handler", Version.create(1, 0, 0), AppHandler.class);

        final Graphic g = Graphics.createGraphic();
        final Services services = new Services();
        final Handler handler = new Handler();
        handler.addUpdatable(new ComponentUpdater());
        handler.addRenderable(new ComponentRenderer());
        handler.add(new MyObject(new Setup(Medias.create("MyObject.xml")), services));
        handler.add(new MyObject(new Setup(Medias.create("MyObject.xml")), services));

        for (int i = 0; i < 2; i++)
        {
            handler.update(1.0);
            handler.render(g);
        }

        Engine.terminate();
    }
}
