package com.jsonmack.mcplugins.harvestxp;

import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfigDecoder;
import com.jsonmack.mcplugins.harvestxp.listener.HarvestBlockListener;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jason MK on 2020-03-13 at 1:22 p.m.
 */
public class HarvestXPPlugin extends JavaPlugin {

    private static final HarvestMaterialConfigDecoder MATERIAL_CONFIG_DECODER = new HarvestMaterialConfigDecoder();

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        getServer().getPluginManager().registerEvents(
                new HarvestBlockListener(this, MATERIAL_CONFIG_DECODER.decodeAll(getConfig())), this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
