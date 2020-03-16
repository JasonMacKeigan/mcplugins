package com.jsonmack.mcplugins.harvestxp.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

/**
 * Created by Jason MK on 2020-03-16 at 10:24 a.m.
 */
public final class HarvestConfigCodec {

    public static final String HOE_TYPE_REDUCING_HARVEST_KEY = "hoe_type_reducing_harvest";

    public static final String HOE_TOOL_REQUIRED_KEY = "hoe_tool_required";

    private final HarvestMaterialConfigCodec materialConfigCodec = new HarvestMaterialConfigCodec();

    private final HarvestToolConfigCodec toolConfigCodec = new HarvestToolConfigCodec();

    public HarvestConfig decode(FileConfiguration config) {
        Set<HarvestMaterialConfig> materialConfigs = materialConfigCodec.decodeAll(config);

        Set<HarvestToolConfig> toolConfigs = toolConfigCodec.decodeAll(config);

        boolean hoeToolRequired = config.getBoolean(HOE_TOOL_REQUIRED_KEY);

        boolean hoeTypeReducesHarvest = config.getBoolean(HOE_TYPE_REDUCING_HARVEST_KEY);

        return new HarvestConfig(materialConfigs, toolConfigs, hoeToolRequired, hoeTypeReducesHarvest);
    }

    public void encode(HarvestConfig config, FileConfiguration configuration) {
        materialConfigCodec.encodeAll(configuration, config.getMaterialConfigs());
        toolConfigCodec.encodeAll(configuration, config.getToolConfigs());

        configuration.set(HOE_TOOL_REQUIRED_KEY, config.isHoeToolRequired());
        configuration.set(HOE_TYPE_REDUCING_HARVEST_KEY, config.isHoeTypeReducingHarvest());
    }

}
