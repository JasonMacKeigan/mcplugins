package com.jsonmack.mcplugins.harvestxp.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-13 at 1:34 p.m.
 */
public class HarvestMaterialConfigDecoder {

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

}
