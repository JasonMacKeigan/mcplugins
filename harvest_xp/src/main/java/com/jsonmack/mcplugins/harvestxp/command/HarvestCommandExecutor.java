package com.jsonmack.mcplugins.harvestxp.command;

import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jason MK on 2020-03-14 at 1:14 p.m.
 */
public class HarvestCommandExecutor implements TabExecutor {

    private final JavaPlugin plugin;

    private final HarvestConfig config;

    public HarvestCommandExecutor(JavaPlugin plugin, HarvestConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        System.out.println(Arrays.toString(strings));

        if (strings.length < 2) {
            return false;
        }
        String key = strings[0];

        String value = strings[1];

        try {
            HarvestMaterialConfigKey materialConfigKey = HarvestMaterialConfigKey.getAllKeys().stream().filter(config ->
                    key.contains(config.getKeyPrefix())).findAny().orElse(null);

            if (materialConfigKey == null) {
                commandSender.sendMessage("No config value found.");
                return false;
            }
            String keyFound = key.equals(materialConfigKey.getHarvestRequiredKey()) ? materialConfigKey.getHarvestRequiredKey()
                    : key.equals(materialConfigKey.getExperienceKey()) ? materialConfigKey.getExperienceKey() : null;

            if (keyFound == null) {
                commandSender.sendMessage("This property cannot be modified");
                return false;
            }
            if (keyFound.equals(materialConfigKey.getExperienceKey())) {
                try {
                    int experience = Integer.parseInt(value);

                    if (experience < 1) {
                        commandSender.sendMessage("Experience must be greater than zero.");
                        return false;
                    }
                    plugin.getConfig().set(keyFound, experience);
                    plugin.saveConfig();
                    commandSender.sendMessage(String.format("The experience gained from harvesting %s is now %s.",
                            materialConfigKey.name().toLowerCase(), experience));
                } catch (NumberFormatException nfe) {
                    return false;
                }
            } else {
                try {
                    int harvestRequired = Integer.parseInt(value);

                    if (harvestRequired < 1) {
                        commandSender.sendMessage("The harvest required must be 1 or greater.");
                        return false;
                    }
                    plugin.getConfig().set(keyFound, harvestRequired);
                    plugin.saveConfig();
                    commandSender.sendMessage(String.format("The harvest required to gain experience for %s is now %s.",
                            materialConfigKey.name().toLowerCase(), harvestRequired));
                } catch (NumberFormatException nfe) {
                    return false;
                }
            }
            return true;
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> results = new ArrayList<>();

        for (HarvestMaterialConfigKey key : HarvestMaterialConfigKey.getAllKeys()) {
            results.add(key.getExperienceKey());
            results.add(key.getHarvestRequiredKey());
        }
        return results;
    }

}
