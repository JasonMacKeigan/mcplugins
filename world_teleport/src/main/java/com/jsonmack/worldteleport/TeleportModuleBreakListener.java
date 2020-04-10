package com.jsonmack.worldteleport;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.InventoryHolder;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Jason MK on 2020-04-07 at 8:05 p.m.
 */
public class TeleportModuleBreakListener implements Listener {

    private final WorldTeleportPlugin plugin;

    public TeleportModuleBreakListener(WorldTeleportPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEnchantingTableBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.ENCHANTING_TABLE) {
            return;
        }
        List<TeleportModule> modules = plugin.getService().getModules();

        TeleportModule module = modules.stream().filter(m -> m.getLocation().getLocation().equals(block.getLocation())).findAny().orElse(null);

        if (module == null) {
            return;
        }
        removeModule(module, event.getPlayer());
    }

    @EventHandler
    public void onDiamondBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.DIAMOND_BLOCK) {
            return;
        }
        List<TeleportModule> modules = plugin.getService().getModules();

        if (modules.isEmpty()) {
            return;
        }
        TeleportModule module = modules.stream()
                .filter(m -> m.getLocation().getLocation().distance(block.getLocation()) <= 2)
                .findAny().orElse(null);

        if (module == null) {
            return;
        }
        Player player = event.getPlayer();

        Set<Block> blocksBelow = LocationUtils.findSurroundingBelow(player.getWorld(), module.getLocation().getLocation(), 1, 0, 1);

        if (blocksBelow.stream().noneMatch(b -> b.equals(block))) {
            return;
        }
        removeModule(module, player);
        player.sendMessage("You broke the teleport modules base, the module is broken.");
    }

    @EventHandler
    public void onCenterBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        Material type = block.getType();

        TeleportModule module = plugin.getService().getMaterialKeys().get(type);

        if (module == null) {
            return;
        }
        Location moduleLocation = module.getLocation().getLocation();

        Location blockLocation = block.getLocation();

        if (blockLocation.getBlockX() != moduleLocation.getX()
                || blockLocation.getZ() != moduleLocation.getZ()
                || blockLocation.getY() != moduleLocation.getY() - 1) {
            return;
        }
        event.getPlayer().sendMessage("You broke the unique teleport module block, the module is broken.");
        removeModule(module, event.getPlayer());
    }

    private void removeModule(TeleportModule module, Player player) {
        plugin.getService().remove(module);

        plugin.getServer().getOnlinePlayers().forEach(p -> {
            InventoryHolder inventoryHolder = p.getOpenInventory().getTopInventory().getHolder();

            if (inventoryHolder instanceof TeleportInventoryHolder) {
                p.closeInventory();
            }
        });
        try {
            plugin.saveModules();
        } catch (IOException e) {
            player.sendMessage("Something went wrong, unable to remove module.");
            e.printStackTrace();
        }
    }
}
