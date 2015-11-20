/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.editor.dialog.map.collision.imports;

import java.io.File;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.editor.dialog.AbstractDialog;
import com.b3dgs.lionengine.editor.dialog.widget.BrowseWidget;
import com.b3dgs.lionengine.editor.project.Project;
import com.b3dgs.lionengine.editor.utility.UtilIcon;
import com.b3dgs.lionengine.editor.world.WorldModel;
import com.b3dgs.lionengine.editor.world.WorldPart;
import com.b3dgs.lionengine.editor.world.handler.SetPointerCollisionHandler;
import com.b3dgs.lionengine.editor.world.handler.SetShowCollisionsHandler;
import com.b3dgs.lionengine.editor.world.tester.MapTester;
import com.b3dgs.lionengine.game.collision.tile.CollisionFormulaConfig;
import com.b3dgs.lionengine.game.collision.tile.CollisionGroupConfig;
import com.b3dgs.lionengine.game.collision.tile.MapTileCollision;
import com.b3dgs.lionengine.game.collision.tile.MapTileCollisionModel;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.MapTileGroup;

/**
 * Represents the import map dialog.
 */
public class CollisionImportDialog extends AbstractDialog
{
    /** Icon. */
    private static final Image ICON = UtilIcon.get("dialog", "import.png");

    /** Formulas config file location. */
    private BrowseWidget formulas;
    /** Collisions config file location. */
    private BrowseWidget collisions;

    /**
     * Create an import map dialog.
     * 
     * @param parent The shell parent.
     */
    public CollisionImportDialog(Shell parent)
    {
        super(parent, Messages.Title, Messages.HeaderTitle, Messages.HeaderDesc, ICON);
        createDialog();
        dialog.setMinimumSize(512, 160);
        finish.setEnabled(false);
        finish.forceFocus();
        loadDefaults();
    }

    /**
     * Get the formulas config file location.
     * 
     * @return The formulas config file location.
     */
    public Media getFormulasLocation()
    {
        return formulas.getMedia();
    }

    /**
     * Get the collisions config file location.
     * 
     * @return The collisions config file location.
     */
    public Media getCollisionsLocation()
    {
        return collisions.getMedia();
    }

    /**
     * Load default files.
     */
    private void loadDefaults()
    {
        final Project project = Project.getActive();
        final MapTile map = WorldModel.INSTANCE.getMap();
        final File parentFile = map.getFeature(MapTileGroup.class).getGroupsConfig().getFile().getParentFile();

        final File formulas = new File(parentFile, CollisionFormulaConfig.FILENAME);
        if (MapTester.isFormulasConfig(project.getResourceMedia(formulas)))
        {
            this.formulas.setLocation(project.getResourceMedia(formulas).getPath());
        }

        final File collisions = new File(parentFile, CollisionGroupConfig.FILENAME);
        if (MapTester.isCollisionsConfig(project.getResourceMedia(collisions)))
        {
            this.collisions.setLocation(project.getResourceMedia(collisions).getPath());
        }
    }

    /**
     * Check if can enable finish button.
     */
    private void checkFinish()
    {
        finish.setEnabled(formulas.getMedia() != null && collisions.getMedia() != null);
    }

    /*
     * AbstractDialog
     */

    @Override
    protected void createContent(Composite content)
    {
        formulas = new BrowseWidget(content, Messages.FormulasLocation, Messages.FormulasConfigFileFilter, true);
        formulas.addListener(media -> checkFinish());

        collisions = new BrowseWidget(content, Messages.CollisionsLocation, Messages.CollisionsFileFilter, true);
        collisions.addListener(media -> checkFinish());
    }

    @Override
    protected void onFinish()
    {
        final MapTile map = WorldModel.INSTANCE.getMap();
        if (!map.hasFeature(MapTileCollision.class))
        {
            map.createFeature(MapTileCollisionModel.class);
        }
        final MapTileCollision mapCollision = map.getFeature(MapTileCollision.class);
        mapCollision.loadCollisions();
        mapCollision.createCollisionDraw();

        final WorldPart part = WorldModel.INSTANCE.getServices().get(WorldPart.class);
        part.setToolItemEnabled(SetShowCollisionsHandler.ID, true);
        part.setToolItemEnabled(SetPointerCollisionHandler.ID, true);
    }
}
