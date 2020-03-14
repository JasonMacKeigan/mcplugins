package com.jsonmack.mcplugins.harvestxp.config;

import org.bukkit.Material;

/**
 * Created by Jason MK on 2020-03-13 at 1:32 p.m.
 */
public class HarvestMaterialConfig {

    private final HarvestMaterialConfigKey key;

    private final Material material;

    private final int amountRequired;

    private final int experience;

    public HarvestMaterialConfig(HarvestMaterialConfigKey key, Material material, int amountRequired, int experience) {
        this.key = key;
        this.material = material;
        this.amountRequired = amountRequired;
        this.experience = experience;
    }

    public HarvestMaterialConfigKey getKey() {
        return key;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmountRequired() {
        return amountRequired;
    }

    public int getExperience() {
        return experience;
    }
}
