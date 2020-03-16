package com.jsonmack.mcplugins.harvestxp.config;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;

import java.util.EnumSet;
import java.util.Set;

import static org.bukkit.Material.*;

/**
 * Created by Jason MK on 2020-03-16 at 10:08 a.m.
 */
public enum HarvestToolConfigKey {
    WOODEN("wood_hoe", WOODEN_HOE),
    STONE("stone_hoe", STONE_HOE),
    GOLDEN("gold_hoe", GOLDEN_HOE),
    IRON("iron_hoe", IRON_HOE),
    DIAMOND("diamond_hoe", DIAMOND_HOE)
    ;

    public static final String REDUCTION_SUFFIX = "reduction";

    private final String prefix;

    private final String reductionKey;

    private final Material material;

    private static final Set<HarvestToolConfigKey> KEYS = ImmutableSet.copyOf(EnumSet.allOf(HarvestToolConfigKey.class));

    HarvestToolConfigKey(String prefix, String reductionKey, Material material) {
        this.prefix = prefix;
        this.reductionKey = reductionKey;
        this.material = material;
    }

    HarvestToolConfigKey(String prefix, Material material) {
        this(prefix, prefix.concat(".").concat(REDUCTION_SUFFIX), material);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getReductionKey() {
        return reductionKey;
    }

    public Material getMaterial() {
        return material;
    }

    public static Set<HarvestToolConfigKey> getKeys() {
        return KEYS;
    }
}
