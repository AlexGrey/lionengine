package com.b3dgs.lionengine.example.c_platform.d_opponent;

import java.util.ArrayList;
import java.util.List;

/**
 * List of tile collisions.
 */
enum TileCollision
{
    /** Ground collision. */
    GROUND,
    /** Block collision. */
    BLOCK,
    /** Wall collision. */
    WALL,
    /** Tube collision. */
    TUBE,
    /** No collision. */
    NONE;

    /** Vertical collisions list. */
    static final List<TileCollision> COLLISION_VERTICAL = new ArrayList<>(2);
    /** Horizontal collisions list. */
    static final List<TileCollision> COLLISION_HORIZONTAL = new ArrayList<>(3);

    /**
     * Static init.
     */
    static
    {
        TileCollision.COLLISION_VERTICAL.add(TileCollision.GROUND);
        TileCollision.COLLISION_VERTICAL.add(TileCollision.BLOCK);
        TileCollision.COLLISION_VERTICAL.add(TileCollision.TUBE);

        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.GROUND);
        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.BLOCK);
        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.TUBE);
        TileCollision.COLLISION_HORIZONTAL.add(TileCollision.WALL);
    }
}
