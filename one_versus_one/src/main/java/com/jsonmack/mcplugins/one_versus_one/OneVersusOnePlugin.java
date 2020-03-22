package com.jsonmack.mcplugins.one_versus_one;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Jason MK on 2020-03-18 at 12:44 p.m.
 */
public class OneVersusOnePlugin extends JavaPlugin {

    public OneVersusOnePlugin() {

    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();

    }

    @Override
    public FileConfiguration getConfig() {
        throw new UnsupportedOperationException("This is not supported.");
    }

    @Override
    public void reloadConfig() {
        throw new UnsupportedOperationException("This is not supported");
    }

    @Override
    public void saveConfig() {
        throw new UnsupportedOperationException("This is not supported");
    }

    @Override
    public void saveDefaultConfig() {
        throw new UnsupportedOperationException("This is not supported.");
    }
}
