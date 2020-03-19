package com.jsonmack.mcplugins.one_versus_one.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.jsonmack.mcplugins.one_versus_one.command.ConfigTextCommandExecutor;
import com.jsonmack.mcplugins.one_versus_one.config.OneVersusOneConfig;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneConfigService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by Jason MK on 2020-03-18 at 9:09 p.m.
 */
public class OneVersusOneConfigServiceModule extends AbstractModule {

    private final File dataFolder;

    private final String configResource;

    private final Gson gson = new GsonBuilder().create();

    public OneVersusOneConfigServiceModule(File dataFolder, String configResource) {
        this.dataFolder = dataFolder;
        this.configResource = configResource;
    }

    @Override
    protected void configure() {
        bind(OneVersusOneConfigService.class)
                .asEagerSingleton();
    }

    @Provides
    public OneVersusOneConfigService oneVersusOneConfigService() {
        return new OneVersusOneConfigService(localFilePath(), config(), defaultConfig());
    }

    private Path localFilePath() {
        return dataFolder.toPath().resolve(configResource);
    }

    private OneVersusOneConfig config() {
        Path dataFolderPath = dataFolder.toPath();

        if (Files.notExists(dataFolderPath)) {
            return defaultConfig();
        }
        Path dataFolderConfigFilePath = dataFolderPath.resolve(configResource);

        if (Files.notExists(dataFolderConfigFilePath)) {
            return defaultConfig();
        }
        try (Reader reader = Files.newBufferedReader(dataFolderConfigFilePath)) {
            return gson.fromJson(reader, OneVersusOneConfig.class);
        } catch (IOException ioe) {
            throw new RuntimeException("Unable to obtain local configuration: " + dataFolderConfigFilePath, ioe);
        }
    }

    private OneVersusOneConfig defaultConfig() {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(getClass().getProtectionDomain()
                .getClassLoader().getResourceAsStream(configResource)))) {
            return gson.fromJson(reader, OneVersusOneConfig.class);
        } catch (IOException ioe) {
            throw new RuntimeException("Unable to obtain default configuration: " + configResource, ioe);
        }
    }
}
