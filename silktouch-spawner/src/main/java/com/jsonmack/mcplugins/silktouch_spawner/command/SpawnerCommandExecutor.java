package com.jsonmack.mcplugins.silktouch_spawner.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jason MK on 2020-03-22 at 3:15 a.m.
 */
public class SpawnerCommandExecutor implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = Arrays.asList(args);

        String type = Arrays.asList(args).stream().findAny().orElse(null);

        if (type == null) {
            sender.sendMessage("You must specify a type to spawn.");
            return true;
        }
        EntityType entityType = EntityType.valueOf(type.toUpperCase());

        Player player = (Player) sender;

//        player.getWorld().spawn(player.getLocation(), CreatureSpawner.class);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("<EntityType>");
        }
        return Collections.emptyList();
    }
}
