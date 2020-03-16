package com.jsonmack.mcplugins.harvestxp.config;

/**
 * Created by Jason MK on 2020-03-16 at 10:08 a.m.
 */
public class HarvestToolConfig {

    private final HarvestToolConfigKey key;

    private final int reduction;

    public HarvestToolConfig(HarvestToolConfigKey key, int reduction) {
        this.key = key;
        this.reduction = reduction;
    }

    public HarvestToolConfig setReduction(int reduction) {
        return new HarvestToolConfig(key, reduction);
    }

    public HarvestToolConfigKey getKey() {
        return key;
    }

    public int getReduction() {
        return reduction;
    }
}
