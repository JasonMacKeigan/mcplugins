package com.jsonmack.mcplugins.harvestxp.config;

import com.google.common.collect.ImmutableSet;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by Jason MK on 2020-03-13 at 1:23 p.m.
 */
public enum HarvestMaterialConfigKey {
    WHEAT("harvest_xp.wheat"),

    CARROT("harvest_xp.carrot"),

    POTATO("harvest_xp.potato"),

    BEETROOT("harvest_xp.beetroot"),

    PUMPKIN("harvest_xp.pumpkin"),

    WATERMELON("harvest_xp.melon");

    private static final String MATERIAL_KEY = "material";

    private static final String EXPERIENCE_KEY = "experience";

    private static final String HARVEST_REQUIRD_KEY = "harvest_required";

    private final String keyPrefix;

    private final String materialKey;

    private final String experienceKey;

    private final String harvestRequiredKey;

    private static final Set<HarvestMaterialConfigKey> KEYS = ImmutableSet.copyOf(EnumSet.allOf(HarvestMaterialConfigKey.class));

    HarvestMaterialConfigKey(String keyPrefix) {
        this.keyPrefix = keyPrefix;
        this.materialKey = keyPrefix.concat(".").concat(MATERIAL_KEY);
        this.experienceKey = keyPrefix.concat(".").concat(EXPERIENCE_KEY);
        this.harvestRequiredKey = keyPrefix.concat(".").concat(HARVEST_REQUIRD_KEY);
    }

    public static Set<HarvestMaterialConfigKey> getAllKeys() {
        return KEYS;
    }

    public String getMaterialKey() {
        return materialKey;
    }

    public String getExperienceKey() {
        return experienceKey;
    }

    public String getHarvestRequiredKey() {
        return harvestRequiredKey;
    }
}
