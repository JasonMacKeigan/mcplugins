package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;

import java.util.concurrent.TimeUnit;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.ok;

/**
 * Created by Jason MK on 2020-03-21 at 12:18 a.m.
 */
public class BedTeleportConfigCooldownUnitListener implements ConfigFieldListener<BedTeleportConfig, TimeUnit> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(TimeUnit value) {
        return ok();
    }

    @Override
    public TimeUnit parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return TimeUnit.valueOf(argument);
        } catch (IllegalStateException ise) {
            throw new ConfigFieldListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, TimeUnit value) {
        return config.withCooldownUnit(value);
    }
}
