package com.jsonmack.mcplugins.bed_teleport.cost;

import org.bukkit.entity.Player;

/**
 * Created by Jason MK on 2020-03-19 at 8:09 p.m.
 */
public interface BedTeleportCostRequirement {

    boolean meets(Player player, int amount);

    void remove(Player player, int amount);

}
