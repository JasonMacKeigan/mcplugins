package com.jsonmack.mcplugins.config;

import org.apache.commons.lang.ClassUtils;
import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by Jason MK on 2020-03-20 at 10:59 p.m.
 */
final class ConfigListenerCollectorImpl<T extends Config> implements ConfigListenerCollector<T> {

    private static final Configuration REFLECTION_CONFIGURATION = new ConfigurationBuilder()
            .addScanners(new FieldAnnotationsScanner())
            .addUrls(ClasspathHelper.forManifest())
            .setExecutorService(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
            .forPackages(".");

    private static final int LISTENER_CLASS_TYPE_PARAMETER_INDEX = 1;

    @SuppressWarnings("unchecked")
    @Override
    public Map<Field, ConfigListener<T, ?>> collect() {
        Reflections reflections = new Reflections(REFLECTION_CONFIGURATION);

        Set<Field> fields = reflections.getFieldsAnnotatedWith(ConfigField.class);

        Map<Field, ConfigListener<T, ?>> listeners = new HashMap<>();

        if (fields.isEmpty()) {
            System.out.println("fields is empty.");
            return listeners;
        }

        for (Field field : fields) {
            ConfigField configField = field.getAnnotation(ConfigField.class);

            Class<? extends ConfigListener<?, ?>> listenerClass = configField.value();

            if (!Modifier.isPublic(listenerClass.getModifiers())) {
                throw new ConfigServiceBuildException(String.format("The class %s must be defined with a public modifier.", listenerClass.getSimpleName()));
            }
            Type listenerClassInterfaceClass = listenerClass.getGenericInterfaces()[0];

            if (listenerClassInterfaceClass == null) {
                throw new ConfigServiceBuildException(String.format("Listener class does not implement %s.", ConfigListener.class.getSimpleName()));
            }
            Type[] listenerClassInterfaceClassTypes = ((ParameterizedType) listenerClassInterfaceClass).getActualTypeArguments();

            Type listenerClassInterfaceClassType = listenerClassInterfaceClassTypes[LISTENER_CLASS_TYPE_PARAMETER_INDEX];

            Type fieldType = field.getType().isPrimitive() ? ClassUtils.primitiveToWrapper(field.getType()) : field.getType();

            if (fieldType != listenerClassInterfaceClassType) {
                throw new ConfigServiceBuildException(String.format("%s defined with type %s but found %s for listener.",
                        ConfigField.class.getSimpleName(),
                        field.getType().getSimpleName(),
                        listenerClassInterfaceClassType.getTypeName()));
            }
            try {
                field.setAccessible(true);

                ConfigListener<T, ?> listener = (ConfigListener<T, ?>) listenerClass.newInstance();

                listeners.put(field, listener);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new ConfigServiceBuildException("Could not create new instance of class, make sure default no-args constructor exists if args constructor exists.", e);
            }
        }
        return listeners;
    }

}