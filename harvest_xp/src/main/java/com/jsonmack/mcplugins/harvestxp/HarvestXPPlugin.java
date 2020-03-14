package com.jsonmack.mcplugins.harvestxp;

import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigDecoder;
import com.jsonmack.mcplugins.harvestxp.listener.HarvestBlockListener;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

/**
 * Created by Jason MK on 2020-03-13 at 1:22 p.m.
 */
public class HarvestXPPlugin extends JavaPlugin {

    private HarvestConfig harvestConfig;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();

        if (harvestConfig != null) {
            HarvestConfig.write(harvestConfig, getConfig());
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        harvestConfig = HarvestConfig.read(getConfig());

        getServer().getPluginManager().registerEvents(new HarvestBlockListener(this, harvestConfig), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
