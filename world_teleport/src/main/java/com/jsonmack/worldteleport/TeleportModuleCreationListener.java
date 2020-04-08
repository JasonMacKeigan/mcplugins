package com.jsonmack.worldteleport;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryType;

import java.io.IOException;
import java.util.HashSet;
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

        Set<Block> surrounding = findSurrounding(locationOfTable.getWorld(), locationOfTable);

        if (surrounding.size() < 9) {
            return;
        }
        if (surrounding.stream().filter(b -> b.getType() == Material.DIAMOND_BLOCK).count() < 8) {
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
        if (service.getModules().stream().anyMatch(module -> module.getLocation().getMaterial() == blockBelowTable.getType())) {
            event.setCancelled(true);
            return;
        }
        if (service.getModules().size() == InventoryType.CHEST.getDefaultSize()) {
            event.setCancelled(true);
            return;
        }
        service.add(new TeleportModule(new TeleportLocation(locationOfTable.clone(), blockBelowTable.getType())));
        try {
            plugin.saveModules();
        } catch (IOException e) {
            event.getPlayer().sendMessage("Unable to create module, something went wrong.");
            event.setCancelled(true);
        }
    }

    private final Set<Block> findSurrounding(World world, Location location) {
        Preconditions.checkNotNull(world, "World is null");

        Location below = new Location(location.getWorld(), location.getX(), location.getY() - 1, location.getZ());

        Set<Block> blocks = new HashSet<>();

        for (int x = below.getBlockX() - 1; x <= below.getBlockX() + 1; x++) {
            for (int z = below.getBlockZ() - 1; z <= below.getBlockZ() + 1; z++) {
                blocks.add(world.getBlockAt(x, below.getBlockY(), z));
            }
        }
        return blocks;
    }

}
