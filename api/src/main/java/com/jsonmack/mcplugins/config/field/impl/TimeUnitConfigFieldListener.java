package com.jsonmack.mcplugins.config.field.impl;

import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerParseException;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-04-11 at 6:57 p.m.
 */
public interface TimeUnitConfigFieldListener<T extends Config> extends ConfigFieldListener<T, TimeUnit> {

    @Override
    default TimeUnit parse(String argument) throws ConfigFieldListenerParseException {
        try {
            return TimeUnit.valueOf(argument);
        } catch (IllegalStateException ise) {
            throw new ConfigFieldListenerParseException(String.format("Unable to parse %s, expected type %s.", argument, TimeUnit.class));
        }
    }

}
