package com.jsonmack.mcplugins.harvestxp.config;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;

import java.util.EnumSet;
import java.util.Set;

import static org.bukkit.Material.MELON;

/**
 * Created by Jason MK on 2020-03-13 at 1:23 p.m.
 */
public enum HarvestMaterialConfigKey {
    WHEAT("wheat", Material.WHEAT),

    CARROT("carrot", Material.CARROT),

    POTATO("potato", Material.POTATO),

    BEETROOT("beetroot", Material.BEETROOT),

    PUMPKIN("pumpkin", Material.PUMPKIN),

    WATERMELON("melon", MELON);

    public static final String EXPERIENCE_KEY = "experience";

    public static final String HARVEST_REQUIRED_KEY = "harvest_required";

    private final String keyPrefix;

    private final String experienceKey;

    private final String harvestRequiredKey;

    private final Material material;

    private static final Set<HarvestMaterialConfigKey> KEYS = ImmutableSet.copyOf(EnumSet.allOf(HarvestMaterialConfigKey.class));

    HarvestMaterialConfigKey(String keyPrefix, Material material) {
        this.keyPrefix = keyPrefix;
        this.material = material;
        this.experienceKey = keyPrefix.concat(".").concat(EXPERIENCE_KEY);
        this.harvestRequiredKey = keyPrefix.concat(".").concat(HARVEST_REQUIRED_KEY);
    }

    public static Set<HarvestMaterialConfigKey> getAllKeys() {
        return KEYS;
    }

    public Material getMaterial() {
        return material;
    }

    public String getExperienceKey() {
        return experienceKey;
    }

    public String getHarvestRequiredKey() {
        return harvestRequiredKey;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }
}
