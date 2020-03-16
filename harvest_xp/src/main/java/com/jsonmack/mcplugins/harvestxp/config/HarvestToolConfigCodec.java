package com.jsonmack.mcplugins.harvestxp.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-16 at 10:14 a.m.
 */
public class HarvestToolConfigCodec {

    public HarvestToolConfig decode(FileConfiguration configuration, HarvestToolConfigKey key) {
        int reduction = configuration.getInt(key.getReductionKey());

        return new HarvestToolConfig(key, reduction);
    }

    public Set<HarvestToolConfig> decodeAll(FileConfiguration configuration) {
        return HarvestToolConfigKey.getKeys()
                .stream()
                .map(key -> decode(configuration, key))
                .collect(Collectors.toSet());
    }

    public void encode(FileConfiguration configuration, HarvestToolConfig config) {
        configuration.set(config.getKey().getReductionKey(), config.getReduction());
    }

    public void encodeAll(FileConfiguration configuration, Set<HarvestToolConfig> configs) {
        configs.forEach(config -> encode(configuration, config));
    }
}
