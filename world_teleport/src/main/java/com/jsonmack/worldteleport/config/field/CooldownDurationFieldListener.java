package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;
import com.jsonmack.mcplugins.config.field.impl.LongConfigFieldListener;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.ok;
import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.unacceptable;

/**
 * Created by Jason MK on 2020-04-11 at 6:28 p.m.
 */
public class CooldownDurationFieldListener implements LongConfigFieldListener<TeleportModuleConfig> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Long value) {
        return value < 0 ? unacceptable("Duration must be non-negative.") : ok();
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, Long value) {
        return config.withCooldownDuration(value);
    }
}
