package com.jsonmack.mcplugins.config.field;

import com.jsonmack.mcplugins.config.Config;

/**
 * Created by Jason MK on 2020-03-20 at 6:55 p.m.
 */
public interface ConfigFieldListener<T extends Config, V> {

    ConfigFieldAcceptanceResult acceptable(V value);

    V parse(String argument) throws ConfigFieldListenerParseException;

    T modify(T config, V value);

}
