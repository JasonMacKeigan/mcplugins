package com.jsonmack.mcplugins.harvestxp.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-13 at 1:34 p.m.
 */
public class HarvestMaterialConfigCodec {

    public HarvestMaterialConfig decode(FileConfiguration configuration, HarvestMaterialConfigKey key) {
        int amountRequired = configuration.getInt(key.getHarvestRequiredKey());

        int experience = configuration.getInt(key.getExperienceKey());

        return new HarvestMaterialConfig(key, key.getMaterial(), amountRequired, experience);
    }

    public Set<HarvestMaterialConfig> decodeAll(FileConfiguration configuration) {
        return HarvestMaterialConfigKey.getAllKeys()
                .stream()
                .map(key -> decode(configuration, key))
                .collect(Collectors.toSet());
    }

    public void encode(FileConfiguration configuration, HarvestMaterialConfig config) {
        HarvestMaterialConfigKey key = config.getKey();

        configuration.set(key.getExperienceKey(), config.getExperience());
        configuration.set(key.getHarvestRequiredKey(), config.getAmountRequired());
    }

    public void encodeAll(FileConfiguration configuration, Set<HarvestMaterialConfig> configs) {
        configs.forEach(config -> encode(configuration, config));
    }

}
