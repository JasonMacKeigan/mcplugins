package com.jsonmack.mcplugins.one_versus_one;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jsonmack.mcplugins.one_versus_one.command.ConfigTextCommandExecutor;
import com.jsonmack.mcplugins.one_versus_one.module.OneVersusOneConfigServiceModule;
import com.jsonmack.mcplugins.one_versus_one.module.OneVersusOneEventServiceModule;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneConfigService;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by Jason MK on 2020-03-18 at 12:44 p.m.
 */
public class OneVersusOnePlugin extends JavaPlugin {

    private static final String CONFIG_FILE = "config.json";

    private Injector injector;

    public OneVersusOnePlugin() {

    }

    @Override
    public void onLoad() {
        super.onLoad();

        injector = Guice.createInjector(
                new OneVersusOneEventServiceModule(),
                new OneVersusOneEventServiceModule(),
                new OneVersusOneConfigServiceModule(getDataFolder(), CONFIG_FILE));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        PluginCommand command = Objects.requireNonNull(getCommand("ovo.config"));

        command.setExecutor(new ConfigTextCommandExecutor(injector));
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
