package com.jsonmack.worldteleport.config;

import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.field.ConfigField;
import com.jsonmack.worldteleport.TeleportModule;
import com.jsonmack.worldteleport.config.field.CooldownDurationFieldListener;
import com.jsonmack.worldteleport.config.field.CooldownEnabledFieldListener;
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

    private static final TeleportModuleConfig DEFAULT_CONFIG = new TeleportModuleConfig(1_000, 60, TimeUnit.SECONDS, true);

    @ConfigField(TilesPerDiamondFieldListener.class)
    private final int tilesPerDiamond;

    @ConfigField(CooldownDurationFieldListener.class)
    private final long cooldownDuration;

    @ConfigField(CooldownUnitFieldListener.class)
    private final TimeUnit cooldownUnit;

    @ConfigField(CooldownEnabledFieldListener.class)
    private final boolean cooldownEnabled;

    public TeleportModuleConfig(int tilesPerDiamond, long cooldownDuration, TimeUnit cooldownUnit, boolean cooldownEnabled) {
        this.tilesPerDiamond = tilesPerDiamond;
        this.cooldownDuration = cooldownDuration;
        this.cooldownUnit = cooldownUnit;
        this.cooldownEnabled = cooldownEnabled;
    }

    public TeleportModuleConfig(Map<String, Object> values) {
        this((int) values.getOrDefault("tiles_per_diamond", DEFAULT_CONFIG.getTilesPerDiamond()),
                Long.parseLong((String) values.getOrDefault("cooldown_duration", Long.toString(DEFAULT_CONFIG.getCooldownDuration()))),
                TimeUnit.valueOf((String) values.getOrDefault("cooldown_unit", DEFAULT_CONFIG.getCooldownUnit().name())),
                (boolean) values.getOrDefault("cooldown_enabled", DEFAULT_CONFIG.isCooldownEnabled()));
    }

    public TeleportModuleConfig withTilesPerDiamond(int tilesPerDiamond) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit, cooldownEnabled);
    }

    public TeleportModuleConfig withCooldownDuration(long cooldownDuration) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit, cooldownEnabled);
    }

    public TeleportModuleConfig withCooldownUnit(TimeUnit cooldownUnit) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit, cooldownEnabled);
    }

    public TeleportModuleConfig withCooldownEnabled(boolean cooldownEnabled) {
        return new TeleportModuleConfig(tilesPerDiamond, cooldownDuration, cooldownUnit, cooldownEnabled);
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

    public boolean isCooldownEnabled() {
        return cooldownEnabled;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> values = new HashMap<>();

        values.put("tiles_per_diamond", tilesPerDiamond);
        values.put("cooldown_duration", Long.toString(cooldownDuration));
        values.put("cooldown_unit", cooldownUnit.name());
        values.put("cooldown_enabled", cooldownEnabled);

        return values;
    }
}
