package com.jsonmack.mcplugins.harvestxp;

import com.jsonmack.mcplugins.harvestxp.command.HarvestCommandExecutor;
import com.jsonmack.mcplugins.harvestxp.command.HarvestConfigCommandExecutor;
import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.listener.HarvestBlockListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

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

        onConfigUpdate();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        harvestConfig = HarvestConfig.read(getConfig());

        onConfigUpdate();
    }

    private void onConfigUpdate() {
        HandlerList.unregisterAll(this);

        getServer().getPluginManager().registerEvents(new HarvestBlockListener(this, harvestConfig), this);

        PluginCommand harvestCommand = getCommand("harvest");

        if (harvestCommand != null) {
            harvestCommand.setExecutor(new HarvestCommandExecutor(this, harvestConfig));
        }
        PluginCommand configCommand = getCommand("harvest_config");

        if (configCommand != null) {
            configCommand.setExecutor(new HarvestConfigCommandExecutor(this, harvestConfig));
        }
    }

    public HarvestXPPlugin setConfig(HarvestConfig harvestConfig) {
        this.harvestConfig = harvestConfig;

        return this;
    }

    public HarvestXPPlugin writeConfig() {
        HarvestConfig.write(harvestConfig, getConfig());

        saveConfig();

        return this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
