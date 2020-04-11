package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.ok;
import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.unacceptable;

/**
 * Created by Jason MK on 2020-04-11 at 6:28 p.m.
 */
public class CooldownDurationFieldListener implements ConfigFieldListener<TeleportModuleConfig, Long> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Long value) {
        return value < 0 ? unacceptable("Duration must be non-negative.") : ok();
    }

    @Override
    public Long parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return Long.parseLong(argument);
        } catch (NumberFormatException nfe) {
            throw new ConfigFieldListenerParseException(String.format("Unable to parse %s, expected Long.", argument));
        }
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, Long value) {
        return config.withCooldownDuration(value);
    }
}
