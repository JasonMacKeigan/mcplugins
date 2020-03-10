package com.jsonmack.mcplugins.plugin;

import com.sun.istack.internal.NotNull;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HomeTeleportPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        super.onLoad();

        getLogger().info(String.format("The %s plugin has been loaded.", getClass().getSimpleName()));
    }

    @Override
    public void onDisable() {
        super.onDisable();

        getLogger().info(String.format("The %s plugin has been disabled.", getClass().getSimpleName()));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getLogger().info(String.format("The %s plugin has been enabled.", getClass().getSimpleName()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("home")) {
            Server server = sender.getServer();

            Player player = server.getPlayer(sender.getName());

            if (player == null) {
                return false;
            }
            int level = player.getLevel();

            if (level < 1) {
                player.sendMessage("You must have a minimum level of 1 to teleport home.");
                return false;
            }
            Location bedLocation = player.getBedSpawnLocation();

            if (bedLocation == null) {
                player.sendMessage("Odd, you don't seem to have a bed spawned or haven't slept in it yet.");
                return false;
            }
            player.giveExpLevels(-1);
            player.teleport(bedLocation);
            player.sendMessage("You have teleported home at the cost of a level.");
            return true;
        }

        return super.onCommand(sender, command, label, args);
    }
}
