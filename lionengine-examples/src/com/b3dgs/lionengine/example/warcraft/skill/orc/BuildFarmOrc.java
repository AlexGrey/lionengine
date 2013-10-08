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
package com.b3dgs.lionengine.example.warcraft.skill.orc;

import com.b3dgs.lionengine.example.warcraft.Cursor;
import com.b3dgs.lionengine.example.warcraft.Map;
import com.b3dgs.lionengine.example.warcraft.skill.SetupSkill;
import com.b3dgs.lionengine.example.warcraft.skill.SkillProduceBuilding;
import com.b3dgs.lionengine.example.warcraft.type.TypeEntity;
import com.b3dgs.lionengine.example.warcraft.type.TypeSkill;

/**
 * Build skill implementation.
 */
final class BuildFarmOrc
        extends SkillProduceBuilding
{
    /**
     * Constructor.
     * 
     * @param setup The setup skill reference.
     * @param cursor The cursor reference.
     * @param map The map reference.
     */
    BuildFarmOrc(SetupSkill setup, Cursor cursor, Map map)
    {
        super(TypeSkill.build_farm_orc, setup, TypeEntity.farm_orc, cursor, map);
    }
}