package com.b3dgs.lionengine.example.c_platform.b_entitycontrol;

import com.b3dgs.lionengine.Config;
import com.b3dgs.lionengine.Display;
import com.b3dgs.lionengine.Engine;
import com.b3dgs.lionengine.Loader;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Version;

// Tutorial: Platform Entity Control
// This tutorial will show how to manage the different state of a controlled entity using keyboard.
// It will also explain how to apply animations depending of its state.

/**
 * Program starts here.
 */
public final class AppPlatformEntityControl
{
    /**
     * Main function.
     * 
     * @param args The arguments.
     */
    public static void main(String args[])
    {
        // Start engine
        Engine.start("Entity control", Version.create(1, 0, 0), Media.getPath("resources", "platform"));

        // Displays
        final Display internal = new Display(320, 240, 16, 60);
        final Display external = new Display(640, 480, 16, 60);

        // Configuration
        final Config config = new Config(internal, external, true);

        // Loader
        final Loader loader = new Loader(config);
        loader.start(new Scene(loader));
    }

    /**
     * Private constructor.
     */
    private AppPlatformEntityControl()
    {
        throw new RuntimeException();
    }
}
