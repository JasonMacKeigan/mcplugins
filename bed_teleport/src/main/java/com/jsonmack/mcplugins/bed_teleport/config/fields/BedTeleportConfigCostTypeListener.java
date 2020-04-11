package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.bed_teleport.cost.BedTeleportCostType;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;

/**
 * Created by Jason MK on 2020-03-21 at 12:07 a.m.
 */
public class BedTeleportConfigCostTypeListener implements ConfigFieldListener<BedTeleportConfig, BedTeleportCostType> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(BedTeleportCostType value) {
        return ConfigFieldAcceptanceResult.ok();
    }

    @Override
    public BedTeleportCostType parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return BedTeleportCostType.valueOf(argument);
        } catch (IllegalStateException e) {
            throw new ConfigFieldListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, BedTeleportCostType value) {
        return config.withCostType(value);
    }
}
