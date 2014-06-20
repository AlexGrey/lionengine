/*
 * Copyright (C) 2013-2014 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.editor.animation;

import java.util.Map;

import com.b3dgs.lionengine.anim.Anim;
import com.b3dgs.lionengine.anim.Animation;
import com.b3dgs.lionengine.editor.ObjectList;
import com.b3dgs.lionengine.game.configurable.Configurable;

/**
 * Represents the animation list, allowing to add and remove animations.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class AnimationList
        extends ObjectList<Animation>
{
    /** Configurable reference. */
    private final Configurable configurable;
    /** Animation properties. */
    private final AnimationProperties animationProperties;

    /**
     * Constructor.
     * 
     * @param configurable The configurable reference.
     * @param animationProperties The animation properties reference.
     */
    public AnimationList(Configurable configurable, AnimationProperties animationProperties)
    {
        this.configurable = configurable;
        this.animationProperties = animationProperties;
    }

    /**
     * Load the existing animations from the entity configurable.
     */
    public void loadAnimations()
    {
        final Map<String, Animation> animations = configurable.getAnimations();
        loadObjects(animations);
    }

    /*
     * ObjectList
     */

    @Override
    protected boolean instanceOf(Object object)
    {
        return object instanceof Animation;
    }

    @Override
    protected Animation cast(Object object)
    {
        return Animation.class.cast(object);
    }

    @Override
    protected Animation copyObject(Animation animation)
    {
        return Anim.createAnimation(animation.getFirst(), animation.getLast(), animation.getSpeed(),
                animation.getReverse(), animation.getRepeat());
    }

    @Override
    protected Animation createDefaultObject()
    {
        return Anim.createAnimation(Animation.MINIMUM_FRAME, Animation.MINIMUM_FRAME + 1, 0.1, false, false);
    }

    @Override
    protected void setSelectedObject(Animation animation)
    {
        super.setSelectedObject(animation);
        animationProperties.setSelectedAnimation(animation);
    }
}
