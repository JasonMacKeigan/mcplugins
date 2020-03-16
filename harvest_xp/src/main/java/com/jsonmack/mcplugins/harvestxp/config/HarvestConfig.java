package com.jsonmack.mcplugins.harvestxp.config;

import com.google.common.collect.ImmutableMap;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-03-13 at 9:22 p.m.
 */
public class HarvestConfig {

    private final Set<HarvestMaterialConfig> materialConfigs;

    private final Set<HarvestToolConfig> toolConfigs;

    private final Map<Material, HarvestMaterialConfig> materialConfigsByMaterial;

    private final Map<Material, HarvestToolConfig> toolConfigsByMaterial;

    private final boolean hoeToolRequired;

    private final boolean hoeTypeReducingHarvest;

    public HarvestConfig(Set<HarvestMaterialConfig> materialConfigs, Set<HarvestToolConfig> toolConfigs, boolean hoeToolRequired, boolean hoeTypeReducingHarvest) {
        this.materialConfigs = materialConfigs;
        this.toolConfigs = toolConfigs;
        this.hoeToolRequired = hoeToolRequired;
        this.hoeTypeReducingHarvest = hoeTypeReducingHarvest;
        this.materialConfigsByMaterial = ImmutableMap.copyOf(materialConfigs
                .stream()
                .collect(Collectors.toMap(HarvestMaterialConfig::getMaterial, Function.identity())));
        this.toolConfigsByMaterial = ImmutableMap.copyOf(toolConfigs
                .stream()
                .collect(Collectors.toMap(config -> config.getKey().getMaterial(), Function.identity())));
    }

    public HarvestConfig replaceHarvestMaterialConfig(HarvestMaterialConfigKey key, HarvestMaterialConfig config) {
        HarvestMaterialConfig existing = materialConfigs.stream()
                .filter(c -> c.getKey() == key)
                .findAny().orElseThrow(IllegalArgumentException::new);

        Set<HarvestMaterialConfig> newConfigs = new HashSet<>(materialConfigs);

        newConfigs.remove(existing);
        newConfigs.add(config);

        return new HarvestConfig(newConfigs, toolConfigs, hoeToolRequired, hoeTypeReducingHarvest);
    }

    public HarvestConfig replaceToolConfig(HarvestToolConfigKey key, HarvestToolConfig config) {
        Set<HarvestToolConfig> newConfigs = Stream.concat(
                toolConfigs.stream().filter(c -> c.getKey() != key),
                Stream.of(config)).collect(Collectors.toSet());

        newConfigs.add(config);

        return new HarvestConfig(materialConfigs, newConfigs, hoeToolRequired, hoeTypeReducingHarvest);
    }

    public HarvestConfig setHoeTypeReducingHarvest(boolean hoeTypeReducingHarvest) {
        return new HarvestConfig(materialConfigs, toolConfigs, hoeToolRequired, hoeTypeReducingHarvest);
    }

    public HarvestConfig setHoeToolRequired(boolean hoeToolRequired) {
        return new HarvestConfig(materialConfigs, toolConfigs, hoeToolRequired, hoeTypeReducingHarvest);
    }

    public Set<HarvestMaterialConfig> getMaterialConfigs() {
        return materialConfigs;
    }

    public Map<Material, HarvestMaterialConfig> getMaterialConfigsByMaterial() {
        return materialConfigsByMaterial;
    }

    public Map<Material, HarvestToolConfig> getToolConfigsByMaterial() {
        return toolConfigsByMaterial;
    }

    public boolean isHoeToolRequired() {
        return hoeToolRequired;
    }

    public boolean isHoeTypeReducingHarvest() {
        return hoeTypeReducingHarvest;
    }

    public Set<HarvestToolConfig> getToolConfigs() {
        return toolConfigs;
    }
}
