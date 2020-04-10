package com.jsonmack.worldteleport;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Jason MK on 2020-04-07 at 6:21 p.m.
 */
public class WorldTeleportPlugin extends JavaPlugin {

    private static final String MODULE_FILE_NAME = "modules.yml";

    private YamlConfiguration moduleServiceConfiguration = new YamlConfiguration();

    private TeleportModuleService service;

    @Override
    public void onEnable() {
        super.onEnable();

        ConfigurationSerialization.registerClass(TeleportLocation.class);
        ConfigurationSerialization.registerClass(TeleportModuleService.class);
        ConfigurationSerialization.registerClass(TeleportModule.class);

        try {
            loadModules();
        } catch (IOException | InvalidConfigurationException e) {
            throw new IllegalStateException("Could not load modules.", e);
        }
        TeleportModuleService existing = moduleServiceConfiguration.getSerializable("modules", TeleportModuleService.class);

        service = existing != null ? existing : new TeleportModuleService(new ArrayList<>());

        getServer().getPluginManager().registerEvents(new TeleportModuleBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportModuleCreationListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportModuleInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new TeleportModuleInterfaceListener(this), this);
    }



    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public void saveModules() throws IOException {
        checkDataFolderExists();

        moduleServiceConfiguration.set("modules", service);
        moduleServiceConfiguration.save(getDataFolder().toPath().resolve(MODULE_FILE_NAME).toFile());
    }

    private void checkDataFolderExists() throws IOException {
        File dataFolder = getDataFolder();

        if (!dataFolder.exists()) {
            Files.createDirectory(dataFolder.toPath());
        }
    }

    private void loadModules() throws IOException, InvalidConfigurationException {
        checkDataFolderExists();

        File moduleFile = getDataFolder().toPath().resolve(MODULE_FILE_NAME).toFile();

        if (!moduleFile.exists()) {
            Files.createFile(moduleFile.toPath());
        }
        moduleServiceConfiguration.load(moduleFile);
    }

    public TeleportModuleService getService() {
        return service;
    }
}
