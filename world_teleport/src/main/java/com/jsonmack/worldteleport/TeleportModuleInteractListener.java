package com.jsonmack.worldteleport;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

/**
 * Created by Jason MK on 2020-04-07 at 7:41 p.m.
 */
public class TeleportModuleInteractListener implements Listener {

    private final TeleportModulePlugin plugin;

    public TeleportModuleInteractListener(TeleportModulePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }
        if (block.getType() != Material.ENCHANTING_TABLE) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        List<TeleportModule> modules = plugin.getService().getModules();

        TeleportModule module = modules.stream().filter(m -> m.getTeleportLocation().getLocation().equals(block.getLocation())).findAny().orElse(null);

        if (module == null) {
            return;
        }
        event.getPlayer().openInventory(new TeleportInventoryHolder(plugin, module, modules).getInventory());
        event.setCancelled(true);
    }
}
