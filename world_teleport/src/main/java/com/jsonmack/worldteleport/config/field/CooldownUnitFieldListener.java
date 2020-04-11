package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import java.util.concurrent.TimeUnit;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.ok;

/**
 * Created by Jason MK on 2020-04-11 at 6:28 p.m.
 */
public class CooldownUnitFieldListener implements ConfigFieldListener<TeleportModuleConfig, TimeUnit> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(TimeUnit value) {
        return ok();
    }

    @Override
    public TimeUnit parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return TimeUnit.valueOf(argument.toUpperCase());
        } catch (IllegalStateException nfe) {
            throw new ConfigFieldListenerParseException(String.format("Unable to parse %s, expected TimeUnit.", argument));
        }
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, TimeUnit value) {
        return config.withCooldownUnit(value);
    }
}
