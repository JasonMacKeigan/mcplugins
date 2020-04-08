package com.jsonmack.mcplugins.silktouch_spawner;

import com.jsonmack.mcplugins.silktouch_spawner.command.SpawnerCommandExecutor;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKeySet;
import com.jsonmack.mcplugins.silktouch_spawner.listener.SpawnerBreakListener;
import com.jsonmack.mcplugins.silktouch_spawner.listener.SpawnerPlaceListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jason MK on 2020-03-22 at 1:21 a.m.
 */
public class SilkTouchSpawnerPlugin extends JavaPlugin {

    private final SpawnerNamespacedKeySet spawnerNamespacedKeySet = new SpawnerNamespacedKeySet(this);

    @Override
    public void onEnable() {
        super.onEnable();

        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new SpawnerBreakListener(this), this);
        manager.registerEvents(new SpawnerPlaceListener(this), this);

        PluginCommand command = getCommand("spawner");

        if (command == null) {
            throw new RuntimeException("Command for spawner is not defined in plugin.");
        }
        command.setExecutor(new SpawnerCommandExecutor());
    }

    public SpawnerNamespacedKeySet getSpawnerNamespacedKeySet() {
        return spawnerNamespacedKeySet;
    }
}