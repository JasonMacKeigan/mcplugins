package com.jsonmack.mcplugins.bed_teleport.cost;

import com.jsonmack.mcplugins.bed_teleport.cost.requirement.BedTeleportDiamondCostRequirement;
import com.jsonmack.mcplugins.bed_teleport.cost.requirement.BedTeleportLevelCostRequirement;

/**
 * Created by Jason MK on 2020-03-19 at 7:45 p.m.
 */
public enum BedTeleportCostType {
    LEVEL(new BedTeleportLevelCostRequirement()),

    DIAMOND(new BedTeleportDiamondCostRequirement());

    private final BedTeleportCostRequirement requirement;

    BedTeleportCostType(BedTeleportCostRequirement requirement) {
        this.requirement = requirement;
    }

    public BedTeleportCostRequirement getRequirement() {
        return requirement;
    }
}
