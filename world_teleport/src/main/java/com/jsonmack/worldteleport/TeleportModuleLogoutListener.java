package com.jsonmack.worldteleport;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Jason MK on 2020-04-11 at 9:50 p.m.
 */
public class TeleportModuleLogoutListener implements Listener {

    private final TeleportModulePlugin plugin;

    public TeleportModuleLogoutListener(TeleportModulePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        plugin.getCooldownService().removeIfExists(event.getPlayer().getUniqueId());
    }

}
