package com.jsonmack.worldteleport.config.field;

import com.jsonmack.mcplugins.config.ConfigListener;
import com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult;
import com.jsonmack.mcplugins.config.ConfigListenerParseException;
import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import static com.jsonmack.mcplugins.config.ConfigListenerAcceptableResult.*;

/**
 * Created by Jason MK on 2020-04-10 at 10:15 p.m.
 */
public class TilesPerDiamondFieldListener implements ConfigListener<TeleportModuleConfig, Integer> {

    @Override
    public ConfigListenerAcceptableResult acceptable(Integer value) {
        return value < 0 ? unacceptable("The value must be non-negative.") : ok();
    }

    @Override
    public Integer parse(String argument) throws ConfigListenerParseException {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException nfe) {
            throw new ConfigListenerParseException();
        }
    }

    @Override
    public TeleportModuleConfig modify(TeleportModuleConfig config, Integer value) {
        return config.setTilesPerDiamond(value);
    }
}
