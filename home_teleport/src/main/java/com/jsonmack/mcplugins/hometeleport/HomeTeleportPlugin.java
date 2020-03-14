package com.jsonmack.mcplugins.hometeleport;

import org.bukkit.Location;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("home")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.isDead()) {
                    return false;
                }
                int level = player.getLevel();

                int levelCost = getConfig().getInt("level-cost", 0);

                if (level < levelCost) {
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
        }

        return super.onCommand(sender, command, label, args);
    }
}
