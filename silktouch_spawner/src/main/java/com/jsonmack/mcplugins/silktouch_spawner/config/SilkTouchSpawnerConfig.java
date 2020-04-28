package com.jsonmack.mcplugins.silktouch_spawner.config;

import com.google.common.collect.ImmutableMap;
import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.field.ConfigField;
import com.jsonmack.mcplugins.silktouch_spawner.config.field.AmountOfSpawnsFieldListener;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

/**
 * Created by Jason MK on 2020-04-15 at 4:14 p.m.
 */
public class SilkTouchSpawnerConfig implements Config, ConfigurationSerializable {

    private static final SilkTouchSpawnerConfig DEFAULT_CONFIG = new SilkTouchSpawnerConfig(1_000);

    @ConfigField(AmountOfSpawnsFieldListener.class)
    private final int amountOfSpawns;

    public SilkTouchSpawnerConfig(int amountOfSpawns) {
        this.amountOfSpawns = amountOfSpawns;
    }

    public SilkTouchSpawnerConfig(Map<String, Object> values) {
        this((int) values.getOrDefault("amountOfSpawns", DEFAULT_CONFIG.amountOfSpawns));
    }

    public SilkTouchSpawnerConfig withAmountOfSpawns(int amountOfSpawns) {
        return new SilkTouchSpawnerConfig(amountOfSpawns);
    }

    public int getAmountOfSpawns() {
        return amountOfSpawns;
    }

    @Override
    public Map<String, Object> serialize() {
        return ImmutableMap.of("amountOfSpawns", amountOfSpawns);
    }

}
