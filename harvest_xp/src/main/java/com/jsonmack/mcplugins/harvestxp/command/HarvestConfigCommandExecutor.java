package com.jsonmack.mcplugins.harvestxp.command;

import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestToolConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Jason MK on 2020-03-14 at 3:41 p.m.
 */
public class HarvestConfigCommandExecutor implements CommandExecutor {

    private final HarvestConfig config;

    public HarvestConfigCommandExecutor(HarvestConfig config) {
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(String.format("The hoe tool %s required.", config.isHoeToolRequired() ? "is" : "is not"));
        commandSender.sendMessage(String.format("The hoe tool type %s reducing harvest required.", config.isHoeTypeReducingHarvest() ? "is" : "is not"));

        for (HarvestMaterialConfig materialConfig : config.getMaterialConfigs()) {
            commandSender.sendMessage(String.format("%s provides %sxp after %s harvests.",
                    materialConfig.getMaterial().name().toLowerCase(),
                    materialConfig.getExperience(),
                    materialConfig.getAmountRequired()));
        }

        for (HarvestToolConfig toolConfig : config.getToolConfigs()) {
            commandSender.sendMessage(String.format("%s tool reduces harvest by %s.",
                    toolConfig.getKey().getMaterial().name().toLowerCase().replace("_", " "),
                    toolConfig.getReduction()));
        }
        return true;
    }

}
