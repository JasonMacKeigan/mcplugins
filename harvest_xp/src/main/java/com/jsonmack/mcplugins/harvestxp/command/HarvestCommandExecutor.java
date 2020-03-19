package com.jsonmack.mcplugins.harvestxp.command;

import com.jsonmack.mcplugins.harvestxp.HarvestXPPlugin;
import com.jsonmack.mcplugins.harvestxp.config.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-14 at 1:14 p.m.
 */
public class HarvestCommandExecutor implements TabExecutor {

    private static final String COMMAND_PERMISSIONS = "harvestxp.config";

    private final HarvestXPPlugin plugin;

    private final HarvestConfig config;

    public HarvestCommandExecutor(HarvestXPPlugin plugin, HarvestConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission(COMMAND_PERMISSIONS)) {
            commandSender.sendMessage("You do not have the permissions required to access this.");
            return false;
        }
        if (strings.length < 2) {
            return false;
        }
        String key = strings[0];

        String value = strings[1];

        if (toolRequiredCommand(key, value, commandSender)) {
            return true;
        }
        if (toolTypeReducingHarvestCommand(key, value, commandSender)) {
            return true;
        }
        if (materialCommand(key, value, commandSender)) {
            return true;
        }
        return toolTypeCommand(key, value, commandSender);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> results = new ArrayList<>();

        if (!commandSender.hasPermission(COMMAND_PERMISSIONS)) {
            return results;
        }
        Set<String> arguments = Arrays.stream(strings).map(String::toLowerCase).collect(Collectors.toSet());

        HarvestMaterialConfigKey foundMaterialKey = HarvestMaterialConfigKey.getAllKeys()
                .stream()
                .filter(k -> arguments.stream().anyMatch(arg -> arg.startsWith(k.getKeyPrefix().toLowerCase())))
                .findAny().orElse(null);

        if (foundMaterialKey != null) {
            if (arguments.stream().anyMatch(arg -> arg.equals(foundMaterialKey.getExperienceKey())
                        || arg.equals(foundMaterialKey.getHarvestRequiredKey()))) {
                results.add("<amount>");

                return results;
            }
            results.add(foundMaterialKey.getExperienceKey());
            results.add(foundMaterialKey.getHarvestRequiredKey());
            return results;
        }
        HarvestToolConfigKey foundToolKey = HarvestToolConfigKey.getKeys()
                .stream()
                .filter(k -> arguments.stream().anyMatch(arg -> arg.startsWith(k.getPrefix().toLowerCase())))
                .findAny().orElse(null);

        if (foundToolKey != null) {
            if (arguments.stream().anyMatch(arg -> arg.equals(foundToolKey.getReductionKey()))) {
                results.add("<amount>");

                return results;
            }
            results.add(foundToolKey.getReductionKey());
            return results;
        }
        HarvestMaterialConfigKey.getAllKeys().forEach(k -> results.add(k.getKeyPrefix()));
        HarvestToolConfigKey.getKeys().forEach(k -> results.add(k.getPrefix()));

        results.add(HarvestConfigCodec.HOE_TOOL_REQUIRED_KEY);
        results.add(HarvestConfigCodec.HOE_TYPE_REDUCING_HARVEST_KEY);

        return results;
    }

    private boolean toolTypeReducingHarvestCommand(String key, String value, CommandSender commandSender) {
        if (key.equals(HarvestConfigCodec.HOE_TYPE_REDUCING_HARVEST_KEY)) {
            boolean hoeTypeReducingHarvest = Boolean.parseBoolean(value);

            commandSender.sendMessage(String.format("Using a hoe tool %s reduces harvest required.",
                    hoeTypeReducingHarvest ? "now" : "no longer"));

            plugin.setConfig(config.setHoeTypeReducingHarvest(true)).writeConfig().reloadConfig();
            return true;
        }
        return false;
    }

    private boolean toolRequiredCommand(String key, String value, CommandSender commandSender) {
        if (key.equals(HarvestConfigCodec.HOE_TOOL_REQUIRED_KEY)) {
            boolean hoeToolRequired = Boolean.parseBoolean(value);

            commandSender.sendMessage(String.format("A hoe tool is %s required.",
                    hoeToolRequired ? "now" : "no longer"));
            plugin.setConfig(config.setHoeToolRequired(hoeToolRequired))
                    .writeConfig().reloadConfig();
            return true;
        }
        return false;
    }

    private boolean toolTypeCommand(String key, String value, CommandSender sender) {
        if (!key.contains(HarvestToolConfigKey.REDUCTION_SUFFIX)) {
            return false;
        }
        try {
            HarvestToolConfigKey toolConfigKey = HarvestToolConfigKey.getKeys().stream().filter(k -> key.contains(k.getPrefix()))
                    .findAny().orElse(null);

            if (toolConfigKey == null) {
                return false;
            }
            String attributeKey = key.contains(HarvestToolConfigKey.REDUCTION_SUFFIX) ? HarvestToolConfigKey.REDUCTION_SUFFIX : null;

            if (attributeKey == null) {
                sender.sendMessage("This property cannot be modified.");
                return false;
            }
            HarvestToolConfig harvestToolConfig = config.getToolConfigs().stream().filter(c -> c.getKey() == toolConfigKey).findAny().orElse(null);

            if (harvestToolConfig == null) {
                return false;
            }
            try {
                int reduction = Integer.parseInt(value);

                sender.sendMessage(String.format("The reduction amount is now %s for the %s tool.",
                        reduction, toolConfigKey.getMaterial().name().toLowerCase().replaceAll("_", " ")));
                plugin.setConfig(config.replaceToolConfig(toolConfigKey, harvestToolConfig.setReduction(reduction)))
                        .writeConfig().reloadConfig();
                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage("Reduction value must be a number equal to or above 0.");
                return false;
            }
        } catch (IllegalStateException ise) {
            return false;
        }
    }

    private boolean materialCommand(String key, String value, CommandSender commandSender) {
        if (!key.contains(HarvestMaterialConfigKey.EXPERIENCE_KEY) && !key.contains(HarvestMaterialConfigKey.HARVEST_REQUIRED_KEY)) {
            return false;
        }
        try {
            HarvestMaterialConfigKey materialConfigKey = HarvestMaterialConfigKey.getAllKeys().stream().filter(config ->
                    key.contains(config.getKeyPrefix())).findAny().orElse(null);

            if (materialConfigKey == null) {
                return false;
            }
            String keyFound = key.equals(materialConfigKey.getHarvestRequiredKey()) ? materialConfigKey.getHarvestRequiredKey()
                    : key.equals(materialConfigKey.getExperienceKey()) ? materialConfigKey.getExperienceKey() : null;

            if (keyFound == null) {
                commandSender.sendMessage("This property cannot be modified");
                return false;
            }
            HarvestMaterialConfig harvestMaterialConfig = config.getMaterialConfigs().stream().filter(c -> c.getKey() == materialConfigKey).findAny().orElse(null);

            if (harvestMaterialConfig == null) {
                return false;
            }
            if (keyFound.equals(materialConfigKey.getExperienceKey())) {
                try {
                    int experience = Integer.parseInt(value);

                    if (experience < 1) {
                        commandSender.sendMessage("Experience must be greater than zero.");
                        return false;
                    }
                    plugin.setConfig(config.replaceHarvestMaterialConfig(materialConfigKey, harvestMaterialConfig.withExperience(experience)))
                            .writeConfig().reloadConfig();
                    commandSender.sendMessage(String.format("The experience gained from harvesting %s is now %s.",
                            materialConfigKey.name().toLowerCase(), experience));
                    return true;
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
                    plugin.setConfig(config.replaceHarvestMaterialConfig(materialConfigKey, harvestMaterialConfig.withAmount(harvestRequired)))
                            .writeConfig().reloadConfig();
                    commandSender.sendMessage(String.format("The harvest required to gain experience for %s is now %s.",
                            materialConfigKey.name().toLowerCase(), harvestRequired));
                    return true;
                } catch (NumberFormatException nfe) {
                    return false;
                }
            }
        } catch (IllegalStateException ise) {
            return false;
        }
    }

}
