package com.jsonmack.worldteleport;

import com.jsonmack.mcplugins.config.ConfigModifiedListener;
import com.jsonmack.mcplugins.config.ConfigService;
import com.jsonmack.mcplugins.config.command.ConfigTabExecutor;
import com.jsonmack.mcplugins.namespaced.NamespacedKeySet;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;
import com.jsonmack.worldteleport.namespaced.TeleportModuleLocatorNamedspacedKey;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import sun.misc.Unsafe;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-04-07 at 6:21 p.m.
 */
public class TeleportModulePlugin extends JavaPlugin implements ConfigModifiedListener<TeleportModuleConfig> {

    private static final String MODULE_FILE_NAME = "modules.yml";

    private final TeleportModuleCooldownService cooldownService = new TeleportModuleCooldownService(this);

    private TeleportModuleConfig config;

    private YamlConfiguration moduleServiceConfiguration = new YamlConfiguration();

    private TeleportModuleService service;

    private ConfigService<TeleportModuleConfig> configService;

    private NamespacedKeySet namespacedKeySet;

    @Override
    public void onEnable() {
        super.onEnable();

        ConfigurationSerialization.registerClass(TeleportModuleConfig.class);
        ConfigurationSerialization.registerClass(TeleportLocation.class);
        ConfigurationSerialization.registerClass(TeleportModuleService.class);
        ConfigurationSerialization.registerClass(TeleportModule.class);

        namespacedKeySet = new NamespacedKeySet(this, Stream.of(TeleportModuleLocatorNamedspacedKey.values())
                .collect(Collectors.toList()));

        registerEnchantments();

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
        getServer().getPluginManager().registerEvents(new TeleportModuleLogoutListener(this), this);
//        getServer().getPluginManager().registerEvents(new TeleportModuleLocatorListener(this), this);

        config = getConfig().getSerializable("config", TeleportModuleConfig.class);

        configService = new ConfigService.Builder<>(config, this).build();

        Objects.requireNonNull(getCommand("teleportmodule")).setExecutor(new ConfigTabExecutor<>(configService));
    }

    private void registerEnchantments() {
        try {
            Field acceptingNowField = Enchantment.class.getDeclaredField("acceptingNew");

            acceptingNowField.setAccessible(true);
            acceptingNowField.set(null, true);

            NamespacedKey tier1Key = namespacedKeySet.get(TeleportModuleLocatorNamedspacedKey.TIER_ONE);

            NamespacedKey tier2Key = namespacedKeySet.get(TeleportModuleLocatorNamedspacedKey.TIER_TWO);

            NamespacedKey tier3Key = namespacedKeySet.get(TeleportModuleLocatorNamedspacedKey.TIER_THREE);

            NamespacedKey tier4Key = namespacedKeySet.get(TeleportModuleLocatorNamedspacedKey.TIER_FOUR);

            NamespacedKey tier5Key = namespacedKeySet.get(TeleportModuleLocatorNamedspacedKey.TIER_FIVE);

//            Enchantment.registerEnchantment(new TeleportModuleLocatorEnchantment(tier1Key, "Module Locator I", 8, 25));
//            Enchantment.registerEnchantment(new TeleportModuleLocatorEnchantment(tier2Key, "Module Locator II", 16, 30));
//            Enchantment.registerEnchantment(new TeleportModuleLocatorEnchantment(tier3Key, "Module Locator III", 30, 60));
//            Enchantment.registerEnchantment(new TeleportModuleLocatorEnchantment(tier4Key, "Module Locator IV", 31, 80));
//            Enchantment.registerEnchantment(new TeleportModuleLocatorEnchantment(tier5Key, "Module Locator V", 40, 100));
        } catch (Exception e) {
            throw new IllegalStateException("Something went wrong registering enchantments.", e);
        }
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

    @Override
    public void onModify(TeleportModuleConfig config) {
        this.config = config;
        getConfig().set("config", config);
        saveConfig();
    }

    public TeleportModuleService getService() {
        return service;
    }

    public TeleportModuleConfig getTeleportModuleConfig() {
        return config;
    }

    public TeleportModuleCooldownService getCooldownService() {
        return cooldownService;
    }

    public NamespacedKeySet getNamespacedKeySet() {
        return namespacedKeySet;
    }
}
