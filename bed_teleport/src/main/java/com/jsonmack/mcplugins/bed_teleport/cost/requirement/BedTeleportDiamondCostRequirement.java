package com.jsonmack.mcplugins.bed_teleport.cost.requirement;

import com.jsonmack.mcplugins.bed_teleport.cost.BedTeleportCostRequirement;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-03-19 at 8:10 p.m.
 */
public class BedTeleportDiamondCostRequirement implements BedTeleportCostRequirement {

    private static final Material DIAMOND_MATERIAL = Material.DIAMOND;

    @Override
    public boolean meets(Player player, int amount) {
        PlayerInventory inventory = player.getInventory();

        if (!inventory.contains(DIAMOND_MATERIAL, amount)) {
            player.sendMessage(String.format("%sYou need at least %s diamonds to teleport to your bed.", ChatColor.RED, amount));
            return false;
        }
        return true;
    }

    @Override
    public void remove(Player player, int amount) {
        PlayerInventory inventory = player.getInventory();

        inventory.removeItem(new ItemStack(DIAMOND_MATERIAL, amount));
    }
}
