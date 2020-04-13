package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;
import com.jsonmack.mcplugins.config.field.impl.IntegerConfigFieldListener;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import static com.jsonmack.mcplugins.config.field.ConfigFieldAcceptanceResult.*;

/**
 * Created by Jason MK on 2020-04-10 at 10:15 p.m.
 */
public class TilesPerDiamondFieldListener implements IntegerConfigFieldListener<TeleportModuleConfig> {

    @Override
    public ConfigFieldAcceptanceResult acceptable(Integer value) {
        return value < 0 ? unacceptable("The value must be non-negative.") : ok();
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, Integer value) {
        return config.withTilesPerDiamond(value);
    }
}
