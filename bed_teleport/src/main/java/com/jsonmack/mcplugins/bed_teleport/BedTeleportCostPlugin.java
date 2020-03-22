package com.jsonmack.mcplugins.bed_teleport;

import com.jsonmack.mcplugins.bed_teleport.command.BedTeleportCommand;
import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.ConfigModifiedListener;
import com.jsonmack.mcplugins.config.ConfigService;
import com.jsonmack.mcplugins.config.command.ConfigTabExecutor;
import com.jsonmack.mcplugins.default_commands.DefaultCommandOmmitListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class BedTeleportCostPlugin extends JavaPlugin implements ConfigModifiedListener<BedTeleportConfig> {

    private static final String BED_COMMAND = "bed";

    private static final String BED_CONFIG_COMMAND = "bed_config";

    private ConfigService<BedTeleportConfig> bedConfigService;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        ConfigurationSerialization.registerClass(BedTeleportConfig.class);

        BedTeleportConfig config = getConfig().getSerializable("config", BedTeleportConfig.class);

        bedConfigService = new ConfigService.Builder<>(config, this).build();

        onConfigUpdate();

        getServer().getPluginManager().registerEvents(new DefaultCommandOmmitListener(this), this);
    }

    private void onConfigUpdate() {
        HandlerList.unregisterAll(this);

        PluginCommand command = getCommand(BED_COMMAND);

        if (command == null) {
            throw new IllegalStateException("/bed command is not defined.");
        }
        command.setExecutor(new BedTeleportCommand(this));

        PluginCommand configCommand = getCommand(BED_CONFIG_COMMAND);

        if (configCommand == null) {
            throw new IllegalStateException("/bed_config command is not defined.");
        }
        configCommand.setExecutor(new ConfigTabExecutor<>(bedConfigService));
    }

    public BedTeleportConfig getBedTeleportConfig() {
        return bedConfigService.getConfig();
    }

    @Override
    public void onModify(BedTeleportConfig config) {
        getConfig().set("config", config);
        saveConfig();
        onConfigUpdate();
    }
}
