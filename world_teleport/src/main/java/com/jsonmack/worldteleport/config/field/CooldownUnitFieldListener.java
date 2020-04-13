package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;
import com.jsonmack.mcplugins.config.field.impl.TimeUnitConfigFieldListener;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import java.util.concurrent.TimeUnit;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.ok;

/**
 * Created by Jason MK on 2020-04-11 at 6:28 p.m.
 */
public class CooldownUnitFieldListener implements TimeUnitConfigFieldListener<TeleportModuleConfig> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(TimeUnit value) {
        return ok();
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, TimeUnit value) {
        return config.withCooldownUnit(value);
    }
}
