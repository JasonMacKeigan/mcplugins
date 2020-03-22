package com.jsonmack.mcplugins.bed_teleport.config;

import com.jsonmack.mcplugins.bed_teleport.config.fields.BedTeleportConfigCooldownDurationListener;
import com.jsonmack.mcplugins.bed_teleport.config.fields.BedTeleportConfigCooldownUnitListener;
import com.jsonmack.mcplugins.bed_teleport.config.fields.BedTeleportConfigCostAmountListener;
import com.jsonmack.mcplugins.bed_teleport.config.fields.BedTeleportConfigCostTypeListener;
import com.jsonmack.mcplugins.bed_teleport.cost.BedTeleportCostType;
import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.ConfigField;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-03-19 at 7:45 p.m.
 */
public class BedTeleportConfig implements ConfigurationSerializable, Config {

    @ConfigField(BedTeleportConfigCostTypeListener.class)
    private final BedTeleportCostType costType;

    @ConfigField(BedTeleportConfigCostAmountListener.class)
    private final int costAmount;

    @ConfigField(BedTeleportConfigCooldownDurationListener.class)
    private final long cooldownDuration;

    @ConfigField(BedTeleportConfigCooldownUnitListener.class)
    private final TimeUnit cooldownUnit;

    public BedTeleportConfig(BedTeleportCostType costType, int costAmount, long cooldownDuration, TimeUnit cooldownUnit) {
        this.costType = costType;
        this.costAmount = costAmount;
        this.cooldownDuration = cooldownDuration;
        this.cooldownUnit = cooldownUnit;
    }

    public BedTeleportConfig(Map<String, Object> map) {
        this(
                BedTeleportCostType.valueOf((String) map.get("cost_type")),
                (int) map.get("cost_amount"),
                Long.parseLong((String) map.get("cooldown_duration")),
                TimeUnit.valueOf((String) map.get("cooldown_unit")));
    }

    public BedTeleportConfig withCostType(BedTeleportCostType costType) {
        return new BedTeleportConfig(costType, costAmount, cooldownDuration, cooldownUnit);
    }

    public BedTeleportConfig withCostAmount(int costAmount) {
        return new BedTeleportConfig(costType, costAmount, cooldownDuration, cooldownUnit);
    }

    public BedTeleportConfig withCooldownDuration(long cooldownDuration) {
        return new BedTeleportConfig(costType, costAmount, cooldownDuration, cooldownUnit);
    }

    public BedTeleportConfig withCooldownUnit(TimeUnit cooldownUnit) {
        return new BedTeleportConfig(costType, costAmount, cooldownDuration, cooldownUnit);
    }

    public BedTeleportCostType getCostType() {
        return costType;
    }

    public int getCostAmount() {
        return costAmount;
    }

    public long getCooldownDuration() {
        return cooldownDuration;
    }

    public TimeUnit getCooldownUnit() {
        return cooldownUnit;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> objects = new HashMap<>();

        objects.put("cost_type", costType.name());
        objects.put("cost_amount", costAmount);
        objects.put("cooldown_duration", Long.toString(cooldownDuration));
        objects.put("cooldown_unit", cooldownUnit.name());

        return objects;
    }
}
