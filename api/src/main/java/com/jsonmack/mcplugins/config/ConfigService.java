package com.jsonmack.mcplugins.config;

import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerCollector;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-20 at 6:59 p.m.
 */
public class ConfigService<T extends Config> {

    //TODO remove type erasure oof, i need more coffee, and more bananas
    private final Map<Field, ConfigFieldListener<T, ?>> listeners;

    //TODO remove listeners if possible and only refer to by String to increase performance, no need to do additional reflection
    private final Map<String, ConfigFieldListener<T, ?>> listenersByFieldName;

    private T config;

    private final ConfigModifiedListener<T> modifiedListener;

    private ConfigService(T config, Map<Field, ConfigFieldListener<T, ?>> listeners, ConfigModifiedListener<T> modifiedListener) {
        this.config = config;
        this.listeners = listeners;
        this.listenersByFieldName = listeners.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().getName(), Map.Entry::getValue));
        this.modifiedListener = modifiedListener;
    }

    public T getConfig() {
        return config;
    }

    public void setConfig(T config) {
        this.config = Objects.requireNonNull(config);
    }

    public Map<String, ConfigFieldListener<T, ?>> getListenersByFieldName() {
        return listenersByFieldName;
    }

    public Map<Field, ConfigFieldListener<T, ?>> getListeners() {
        return listeners;
    }

    public ConfigModifiedListener<T> getModifiedListener() {
        return modifiedListener;
    }

    public final static class Builder<T extends Config> {

        private final Map<Field, ConfigFieldListener<T, ?>> listeners;

        private final T config;

        private final ConfigModifiedListener<T> modifiedListener;

        public Builder(T config, ConfigFieldListenerCollector<T> collector, ConfigModifiedListener<T> modifiedListener) {
            this.config = config;
            this.listeners = collector.collect();
            this.modifiedListener = modifiedListener;
        }

        public Builder(T config, ConfigModifiedListener<T> modifiedListener) {
            this(config, new ConfigListenerCollectorImpl<>(), modifiedListener);
        }

        public ConfigService<T> build() {
            return new ConfigService<>(config, listeners, modifiedListener);
        }
    }

}
