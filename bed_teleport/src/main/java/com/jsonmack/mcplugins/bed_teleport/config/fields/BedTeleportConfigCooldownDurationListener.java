package com.jsonmack.mcplugins.bed_teleport.config.fields;

import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.*;

/**
 * Created by Jason MK on 2020-03-21 at 12:15 a.m.
 */
public class BedTeleportConfigCooldownDurationListener implements ConfigFieldListener<BedTeleportConfig, Long> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Long value) {
        return value < 0 ? unacceptable("The cooldown duration must be greater than or equal to zero.") : ok();
    }

    @Override
    public Long parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return Long.parseLong(argument);
        } catch (NumberFormatException nfe) {
            throw new ConfigFieldListenerParseException();
        }
    }

    @Override
    public BedTeleportConfig modify(BedTeleportConfig config, Long value) {
        return config.withCooldownDuration(value);
    }
}
