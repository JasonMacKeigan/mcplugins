package com.jsonmack.mcplugins.harvestxp.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-13 at 9:22 p.m.
 */
public class HarvestConfig {

    private final Set<HarvestMaterialConfig> materialConfigs;

    private final Map<Material, HarvestMaterialConfig> materialConfigsByMaterial;

    private final boolean hoeToolRequired;

    public HarvestConfig(Set<HarvestMaterialConfig> materialConfigs, boolean hoeToolRequired) {
        this.materialConfigs = materialConfigs;
        this.hoeToolRequired = hoeToolRequired;
        this.materialConfigsByMaterial = materialConfigs.stream()
                .collect(Collectors.toMap(HarvestMaterialConfig::getMaterial, Function.identity()));
    }

    public static HarvestConfig read(FileConfiguration config) {
        Set<HarvestMaterialConfig> materialConfigs = new HarvestMaterialConfigDecoder().decodeAll(config);

        boolean hoeToolRequired = config.getBoolean("harvest_xp.hoe_tool_required");

        return new HarvestConfig(materialConfigs, hoeToolRequired);
    }

    public static void write(HarvestConfig config, FileConfiguration configuration) {
        for (HarvestMaterialConfig materialConfig : config.getMaterialConfigs()) {
            HarvestMaterialConfigKey key = materialConfig.getKey();

            configuration.set(key.getMaterialKey(), materialConfig.getMaterial().name());
            configuration.set(key.getExperienceKey(), materialConfig.getExperience());
            configuration.set(key.getHarvestRequiredKey(), materialConfig.getAmountRequired());
        }
        configuration.set("harvest_xp.hoe_tool_required", config.isHoeToolRequired());
    }

    public Set<HarvestMaterialConfig> getMaterialConfigs() {
        return materialConfigs;
    }

    public Map<Material, HarvestMaterialConfig> getMaterialConfigsByMaterial() {
        return materialConfigsByMaterial;
    }

    public boolean isHoeToolRequired() {
        return hoeToolRequired;
    }
}
