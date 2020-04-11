package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.*;

/**
 * Created by Jason MK on 2020-03-21 at 12:12 a.m.
 */
public class BedTeleportConfigCostAmountListener implements ConfigFieldListener<BedTeleportConfig, Integer> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Integer value) {
        return value < 0 ? unacceptable("The cost must be greater than or equal to zero.") : ok();
    }

    @Override
    public Integer parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException nfe) {
            throw new ConfigFieldListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, Integer value) {
        return config.withCostAmount(value);
    }
}
