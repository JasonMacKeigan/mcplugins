package com.jsonmack.worldteleport.config;

import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.field.ConfigField;
import com.jsonmack.worldteleport.config.field.CooldownDurationFieldListener;
import com.jsonmack.worldteleport.config.field.CooldownUnitFieldListener;
import com.jsonmack.worldteleport.config.field.TilesPerDiamondFieldListener;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-04-10 at 10:07 p.m.
 */
public class TeleportModuleConfig implements Config, ConfigurationSerializable {

    @ConfigField(value = TilesPerDiamondFieldListener.class)
    private final int tilesPerDiamond;

    @ConfigField(value = CooldownDurationFieldListener.class)
    private final long cooldownDuration;

    @ConfigField(value = CooldownUnitFieldListener.class)
    private final TimeUnit cooldownUnit;

    public TeleportModuleConfig(int tilesPerDiamond, long cooldownDuration, TimeUnit cooldownUnit) {
        this.tilesPerDiamond = tilesPerDiamond;
        this.cooldownDuration = cooldownDuration;
        this.cooldownUnit = cooldownUnit;
    }

    public TeleportModuleConfig(Map<String, Object> values) {
        this((int) values.getOrDefault("tiles_per_diamond", 1_000),
                Long.parseLong((String) values.getOrDefault("cooldown_duration", "60")),
                TimeUnit.valueOf((String) values.getOrDefault("cooldown_unit", "SECONDS")));
    }

    public TeleportModuleConfig withTilesPerDiamond(int tilesPerDiamond) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit);
    }

    public TeleportModuleConfig withCooldownDuration(long cooldownDuration) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit);
    }

    public TeleportModuleConfig withCooldownUnit(TimeUnit cooldownUnit) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit);
    }

    public int getTilesPerDiamond() {
        return tilesPerDiamond;
    }

    public long getCooldownDuration() {
        return cooldownDuration;
    }

    public TimeUnit getCooldownUnit() {
        return cooldownUnit;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> values = new HashMap<>();

        values.put("tiles_per_diamond", tilesPerDiamond);
        values.put("cooldown_duration", Long.toString(cooldownDuration));
        values.put("cooldown_unit", cooldownUnit.name());

        return values;
    }
}
