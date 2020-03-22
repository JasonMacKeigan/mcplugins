package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.ConfigListener;
import com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult;
import com.jsonmack.mcplugins.config.ConfigListenerParseException;

import java.util.concurrent.TimeUnit;

import static com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult.ok;

/**
 * Created by Jason MK on 2020-03-21 at 12:18 a.m.
 */
public class BedTeleportConfigCooldownUnitListener implements ConfigListener<BedTeleportConfig, TimeUnit> {

    @Override
    public ConfigListenerAcceptableResult acceptable(TimeUnit value) {
        return ok();
    }

    @Override
    public TimeUnit parse(String argument) throws ConfigListenerParseException {
        try {
            return TimeUnit.valueOf(argument);
        } catch (IllegalStateException ise) {
            throw new ConfigListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, TimeUnit value) {
        return config.withCooldownUnit(value);
    }
}
