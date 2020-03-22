package com.jsonmack.mcplugins.bed_teleport.cost.requirement;

import com.jsonmack.mcplugins.bed_teleport.cost.BedTeleportCostRequirement;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Jason MK on 2020-03-19 at 8:13 p.m.
 */
public class BedTeleportLevelCostRequirement implements BedTeleportCostRequirement {

    @Override
    public boolean meets(Player player, int amount) {
        int level = player.getLevel();

        if (level < amount) {
            player.sendMessage(String.format("%sYou need at least %s levels to teleport to your bed.", ChatColor.RED, amount));
            return false;
        }
        return true;
    }

    @Override
    public void remove(Player player, int amount) {
        player.giveExpLevels(-1);
    }
}
