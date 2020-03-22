package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.ConfigListener;
import com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult;
import com.jsonmack.mcplugins.config.ConfigListenerParseException;

import static com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult.*;

/**
 * Created by Jason MK on 2020-03-21 at 12:15 a.m.
 */
public class BedTeleportConfigCooldownDurationListener implements ConfigListener<BedTeleportConfig, Long> {

    @Override
    public ConfigListenerAcceptableResult acceptable(Long value) {
        return value < 0 ? unacceptable("The cooldown duration must be greater than or equal to zero.") : ok();
    }

    @Override
    public Long parse(String argument) throws ConfigListenerParseException {
        try {
            return Long.parseLong(argument);
        } catch (NumberFormatException nfe) {
            throw new ConfigListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, Long value) {
        return config.withCooldownDuration(value);
    }
}
