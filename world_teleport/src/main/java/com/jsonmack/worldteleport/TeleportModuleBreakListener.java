package com.jsonmack.worldteleport;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.util.List;

/**
 * Created by Jason MK on 2020-04-07 at 8:05 p.m.
 */
public class TeleportModuleBreakListener implements Listener {

    private final WorldTeleportPlugin plugin;

    public TeleportModuleBreakListener(WorldTeleportPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.ENCHANTING_TABLE) {
            return;
        }
        List<TeleportModule> modules = plugin.getService().getModules();

        TeleportModule module = modules.stream().filter(m -> m.getLocation().getLocation().equals(block.getLocation())).findAny().orElse(null);

        if (module == null) {
            return;
        }
        plugin.getService().remove(module);
        try {
            plugin.saveModules();
        } catch (IOException e) {
            event.getPlayer().sendMessage("Something went wrong, unable to remove module.");
            e.printStackTrace();
        }
    }
}
