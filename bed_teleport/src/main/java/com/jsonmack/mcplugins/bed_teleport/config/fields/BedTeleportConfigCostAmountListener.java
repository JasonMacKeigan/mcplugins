package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.ConfigListener;
import com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult;
import com.jsonmack.mcplugins.config.ConfigListenerParseException;

import static com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult.*;

/**
 * Created by Jason MK on 2020-03-21 at 12:12 a.m.
 */
public class BedTeleportConfigCostAmountListener implements ConfigListener<BedTeleportConfig, Integer> {

    @Override
    public ConfigListenerAcceptableResult acceptable(Integer value) {
        return value < 0 ? unacceptable("The cost must be greater than or equal to zero.") : ok();
    }

    @Override
    public Integer parse(String argument) throws ConfigListenerParseException {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException nfe) {
            throw new ConfigListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, Integer value) {
        return config.withCostAmount(value);
    }
}
