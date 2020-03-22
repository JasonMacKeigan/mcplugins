package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.bed_teleport.cost.BedTeleportCostType;
import com.jsonmack.mcplugins.config.ConfigListener;
import com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult;
import com.jsonmack.mcplugins.config.ConfigListenerParseException;

/**
 * Created by Jason MK on 2020-03-21 at 12:07 a.m.
 */
public class BedTeleportConfigCostTypeListener implements ConfigListener<BedTeleportConfig, BedTeleportCostType> {

    @Override
    public ConfigListenerAcceptableResult acceptable(BedTeleportCostType value) {
        return ConfigListenerAcceptableResult.ok();
    }

    @Override
    public BedTeleportCostType parse(String argument) throws ConfigListenerParseException {
        try {
            return BedTeleportCostType.valueOf(argument);
        } catch (IllegalStateException e) {
            throw new ConfigListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, BedTeleportCostType value) {
        return config.withCostType(value);
    }
}
