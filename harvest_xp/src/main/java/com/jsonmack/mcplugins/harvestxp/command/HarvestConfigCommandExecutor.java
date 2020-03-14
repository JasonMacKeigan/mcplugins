package com.jsonmack.mcplugins.harvestxp.command;

import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jason MK on 2020-03-14 at 3:41 p.m.
 */
public class HarvestConfigCommandExecutor implements CommandExecutor {

    private final JavaPlugin plugin;

    private final HarvestConfig config;

    public HarvestConfigCommandExecutor(JavaPlugin plugin, HarvestConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        commandSender.sendMessage(String.format("The hoe tool %s required.", config.isHoeToolRequired() ? "is" : "is not"));

        for (HarvestMaterialConfig materialConfig : config.getMaterialConfigs()) {
            commandSender.sendMessage(String.format("%s provides %sxp after %s harvests.",
                    materialConfig.getMaterial(), materialConfig.getExperience(), materialConfig.getAmountRequired()));
        }
        return true;
    }

}
