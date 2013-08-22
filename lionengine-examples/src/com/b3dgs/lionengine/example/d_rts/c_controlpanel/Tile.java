package com.b3dgs.lionengine.example.d_rts.c_controlpanel;

import com.b3dgs.lionengine.game.rts.map.TileRts;

/**
 * Tile implementation.
 */
final class Tile
        extends TileRts<TileCollision, TypeResource>
{
    /**
     * Constructor.
     * 
     * @param width The tile width.
     * @param height The tile height.
     */
    Tile(int width, int height)
    {
        super(width, height);
    }

    /*
     * TileRts
     */

    @Override
    public TileCollision getCollisionFrom(String collision, String type)
    {
        try
        {
            return TileCollision.valueOf(type);
        }
        catch (IllegalArgumentException
               | NullPointerException exception)
        {
            return TileCollision.NONE;
        }
    }

    @Override
    public void checkResourceType(TileCollision collision)
    {
        setResourceType(TypeResource.NONE);
    }

    @Override
    public boolean checkBlocking(TileCollision collision)
    {
        return true;
    }

    @Override
    public boolean hasResources()
    {
        return getResourceType() != TypeResource.NONE;
    }
}
