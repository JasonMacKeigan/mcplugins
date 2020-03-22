package com.jsonmack.mcplugins.default_commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Jason MK on 2020-03-21 at 11:00 p.m.
 */
public class DefaultCommandOmmitListener implements Listener {

    private final JavaPlugin plugin;

    private final String pluginName;

    public DefaultCommandOmmitListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginName = plugin.getName().toLowerCase(Locale.ENGLISH);
    }

    @EventHandler
    public void on(PlayerCommandSendEvent event) {
        Collection<String> commands = event.getCommands();

        commands.removeIf(name -> name.startsWith(pluginName));
    }

}
