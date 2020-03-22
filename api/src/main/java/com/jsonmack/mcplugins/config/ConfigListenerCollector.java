package com.jsonmack.mcplugins.config;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Jason MK on 2020-03-20 at 10:53 p.m.
 */
public interface ConfigListenerCollector<T extends Config> {

    Map<Field, ConfigListener<T, ?>> collect();

}
