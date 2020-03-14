package com.jsonmack.mcplugins.harvestxp.command;

import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason MK on 2020-03-14 at 1:14 p.m.
 */
public class HarvestConfigCommandExecutor implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> results = new ArrayList<>();

        for (HarvestMaterialConfigKey key : HarvestMaterialConfigKey.getAllKeys()) {
            results.add(key.getKeyPrefix());
            results.add(key.getExperienceKey());
            results.add(key.getHarvestRequiredKey());
        }
        return results;
    }

}
