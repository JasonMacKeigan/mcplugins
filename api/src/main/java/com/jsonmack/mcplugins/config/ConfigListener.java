package com.jsonmack.mcplugins.config;

/**
 * Created by Jason MK on 2020-03-20 at 6:55 p.m.
 */
public interface ConfigListener<T extends Config, V> {

    ConfigListenerAcceptableResult acceptable(V value);

    V parse(String argument) throws ConfigListenerParseException;

    T modify(T config, V value);

}
