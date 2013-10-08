/*
 * Copyright (C) 2013 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.game.rts.skills.weapon;

import com.b3dgs.lionengine.example.game.rts.skills.Context;
import com.b3dgs.lionengine.example.game.rts.skills.ProjectileType;
import com.b3dgs.lionengine.example.game.rts.skills.WeaponType;
import com.b3dgs.lionengine.example.game.rts.skills.entity.Entity;
import com.b3dgs.lionengine.example.game.rts.skills.projectile.LauncherProjectile;
import com.b3dgs.lionengine.game.rts.ability.attacker.AttackerUsedServices;

/**
 * Spear weapon implementation.
 */
final class Spear
        extends Weapon
{
    /** Launcher instance. */
    private final LauncherProjectile launcher;

    /**
     * Constructor.
     * 
     * @param user The user reference.
     * @param context The context reference.
     */
    Spear(AttackerUsedServices<Entity> user, Context context)
    {
        super(WeaponType.SPEAR, user, context);
        launcher = new LauncherProjectile(ProjectileType.SPEAR, context);
        launcher.setOwner(this);
        launcher.setCanHitTargetOnly(true);
    }

    /*
     * Weapon
     */

    @Override
    public void notifyAttackEnded(int damages, Entity target)
    {
        launcher.launch(target);
    }
}