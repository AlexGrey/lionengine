package com.b3dgs.lionengine.example.d_rts.f_warcraft.skill.human;

import com.b3dgs.lionengine.example.d_rts.f_warcraft.Cursor;
import com.b3dgs.lionengine.example.d_rts.f_warcraft.Map;
import com.b3dgs.lionengine.example.d_rts.f_warcraft.skill.SetupSkill;
import com.b3dgs.lionengine.example.d_rts.f_warcraft.skill.SkillProduceBuilding;
import com.b3dgs.lionengine.example.d_rts.f_warcraft.type.TypeEntity;
import com.b3dgs.lionengine.example.d_rts.f_warcraft.type.TypeSkill;

/**
 * Build skill implementation.
 */
final class BuildBarracksHuman
        extends SkillProduceBuilding
{
    /**
     * Constructor.
     * 
     * @param setup The setup skill reference.
     * @param cursor The cursor reference.
     * @param map The map reference.
     */
    BuildBarracksHuman(SetupSkill setup, Cursor cursor, Map map)
    {
        super(TypeSkill.build_barracks_human, setup, TypeEntity.barracks_human, cursor, map);
    }
}
