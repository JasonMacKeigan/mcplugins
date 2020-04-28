package com.jsonmack.mcplugins.silktouch_spawner;

import com.jsonmack.mcplugins.config.ConfigModifiedListener;
import com.jsonmack.mcplugins.config.ConfigService;
import com.jsonmack.mcplugins.silktouch_spawner.config.SilkTouchSpawnerConfig;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKeySet;
import com.jsonmack.mcplugins.silktouch_spawner.listener.SpawnerBreakListener;
import com.jsonmack.mcplugins.silktouch_spawner.listener.SpawnerPlaceListener;
import com.jsonmack.mcplugins.silktouch_spawner.listener.SpawnerReductionEvent;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jason MK on 2020-03-22 at 1:21 a.m.
 */
public class SilkTouchSpawnerPlugin extends JavaPlugin implements ConfigModifiedListener<SilkTouchSpawnerConfig> {

    private final SpawnerNamespacedKeySet spawnerNamespacedKeySet = new SpawnerNamespacedKeySet(this);

    private SilkTouchSpawnerConfig config;

    private ConfigService<SilkTouchSpawnerConfig> configService;

    @Override
    public void onEnable() {
        super.onEnable();

        ConfigurationSerialization.registerClass(SilkTouchSpawnerConfig.class);

        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new SpawnerBreakListener(this), this);
        manager.registerEvents(new SpawnerPlaceListener(this), this);
        manager.registerEvents(new SpawnerReductionEvent(this), this);

        config = getConfig().getSerializable("config", SilkTouchSpawnerConfig.class);

        configService = new ConfigService.Builder<>(config, this).build();
    }

    public SpawnerNamespacedKeySet getSpawnerNamespacedKeySet() {
        return spawnerNamespacedKeySet;
    }

    @Override
    public void onModify(SilkTouchSpawnerConfig config) {
        this.config = config;
        this.saveConfig();
    }

    public SilkTouchSpawnerConfig getSpawnerConfig() {
        return config;
    }
}
