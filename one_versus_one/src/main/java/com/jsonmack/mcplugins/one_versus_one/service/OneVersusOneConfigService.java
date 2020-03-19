package com.jsonmack.mcplugins.one_versus_one.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.jsonmack.mcplugins.one_versus_one.config.OneVersusOneConfig;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Created by Jason MK on 2020-03-18 at 9:08 p.m.
 */
@Singleton
public class OneVersusOneConfigService {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Path localFilePath;

    private final OneVersusOneConfig defaultConfig;

    private OneVersusOneConfig config;

    @Inject
    public OneVersusOneConfigService(Path localFilePath,
                                     @Named("config") OneVersusOneConfig config,
                                     @Named("defaultConfig") OneVersusOneConfig defaultConfig) {
        this.localFilePath = localFilePath;
        this.config = config;
        this.defaultConfig = defaultConfig;
    }

    public void write() throws IOException {
        Path parentFolderPath = localFilePath.getParent();

        if (Files.notExists(parentFolderPath)) {
            Files.createDirectories(parentFolderPath);
        }
        try (Writer writer = Files.newBufferedWriter(localFilePath, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
            gson.toJson(config, writer);
        }
    }

    public void setConfig(OneVersusOneConfig config) {
        this.config = config;
    }

    public void setConfigAndWrite(OneVersusOneConfig config) throws IOException {
        this.config = config;
        write();
    }

    public OneVersusOneConfig getDefaultConfig() {
        return defaultConfig;
    }

    public OneVersusOneConfig getConfig() {
        return config;
    }
}
