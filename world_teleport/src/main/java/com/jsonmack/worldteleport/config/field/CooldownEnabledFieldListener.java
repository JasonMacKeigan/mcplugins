package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.impl.BooleanConfigFieldListener;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.*;

/**
 * Created by Jason MK on 2020-04-13 at 12:31 p.m.
 */
public class CooldownEnabledFieldListener implements BooleanConfigFieldListener<TeleportModuleConfig> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Boolean value) {
        return ok();
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, Boolean value) {
        return config.withCooldownEnabled(value);
    }

}
