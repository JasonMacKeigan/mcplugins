package com.jsonmack.worldteleport;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.util.BoundingBox;

import java.io.IOException;
import java.util.Set;

/**
 * Created by Jason MK on 2020-04-07 at 6:00 p.m.
 */
public class TeleportModuleCreationListener implements Listener {

    private final WorldTeleportPlugin plugin;

    public TeleportModuleCreationListener(WorldTeleportPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.ENCHANTING_TABLE) {
            return;
        }
        World world = block.getWorld();

        Location locationOfTable = block.getLocation().clone();

        Set<Block> surrounding = LocationUtils.findSurroundingBelow(locationOfTable.getWorld(), locationOfTable, 1, 0, 1);

        Player player = event.getPlayer();

        if (surrounding.size() < 9) {
            return;
        }
        if (surrounding.stream().filter(b -> b.getType() == Material.DIAMOND_BLOCK).count() < 8) {
            return;
        }
        if (surrounding.stream().anyMatch(Block::isEmpty)) {
            return;
        }
        Block blockBelowTable = world.getBlockAt(locationOfTable.getBlockX(),
                locationOfTable.getBlockY() - 1, locationOfTable.getBlockZ());

        if (blockBelowTable.isEmpty()) {
            return;
        }
        TeleportModuleService service = plugin.getService();

        if (service == null) {
            return;
        }
        if (blockBelowTable.isLiquid()) {
            player.sendMessage("The center block cannot be lava or water.");
            event.setCancelled(true);
            return;
        }
        BoundingBox blockBelowBoudningBox = blockBelowTable.getBoundingBox();

        if (blockBelowBoudningBox.getWidthX() != 1 || blockBelowBoudningBox.getWidthZ() != 1|| blockBelowBoudningBox.getHeight() != 1) {
            player.sendMessage("The center block must be square. It is too small or too big.");
            event.setCancelled(true);
            return;
        }
        if (!blockBelowTable.getType().isSolid()) {
            player.sendMessage("The center block must be solid.");
            event.setCancelled(true);
            return;
        }

        if (service.getModules().stream().anyMatch(module -> module.getTeleportLocation().getMaterial() == blockBelowTable.getType())) {
            player.sendMessage("You already have a teleport module with this unique type.");
            event.setCancelled(true);
            return;
        }
        if (service.getModules().size() == InventoryType.CHEST.getDefaultSize()) {
            player.sendMessage("You already have the maximum number of modules possible.");
            event.setCancelled(true);
            return;
        }
        player.sendMessage("You have successfully created a teleport module.");
        service.add(new TeleportModule(new TeleportLocation(locationOfTable.clone(), blockBelowTable.getType())));
        try {
            plugin.saveModules();
        } catch (IOException e) {
            event.getPlayer().sendMessage("Unable to create module, something went wrong.");
            event.setCancelled(true);
        }
    }

}
