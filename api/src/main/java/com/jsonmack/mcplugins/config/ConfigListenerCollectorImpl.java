package com.jsonmack.mcplugins.config;

import com.jsonmack.mcplugins.config.field.ConfigField;
import com.jsonmack.mcplugins.config.field.ConfigFieldListener;
import com.jsonmack.mcplugins.config.field.ConfigFieldListenerCollector;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;
import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-03-20 at 10:59 p.m.
 */
final class ConfigListenerCollectorImpl<T extends Config> implements ConfigFieldListenerCollector<T> {

    private static final int LISTENER_CLASS_TYPE_PARAMETER_INDEX = 1;

    @SuppressWarnings("unchecked")
    @Override
    public Map<Field, ConfigFieldListener<T, ?>> collect() {
        try (ScanResult result = new ClassGraph().enableAllInfo().whitelistPackages("*").scan()) {
            ClassInfoList classesWithConfigField = result.getClassesWithFieldAnnotation(ConfigField.class.getName());

            List<Field> fields = classesWithConfigField
                    .stream()
                    .flatMap(classInfo -> classInfo.getFieldInfo().filter(filter ->
                            filter.hasAnnotation(ConfigField.class.getName())).stream())
                    .map(FieldInfo::loadClassAndGetField)
                    .collect(Collectors.toList());

            Map<Field, ConfigFieldListener<T, ?>> listeners = new HashMap<>();

            if (fields.isEmpty()) {
                return listeners;
            }

            for (Field field : fields) {
                ConfigField configField = field.getAnnotation(ConfigField.class);

                Class<? extends ConfigFieldListener<?, ?>> listenerClass = configField.value();

                if (!Modifier.isPublic(listenerClass.getModifiers())) {
                    throw new ConfigServiceBuildException(
                            String.format("The class %s must be defined with a public modifier.", listenerClass.getSimpleName()));
                }
                Type listenerClassInterfaceClass = listenerClass.getGenericInterfaces()[0];

                if (listenerClassInterfaceClass == null) {
                    throw new ConfigServiceBuildException(String.format("Listener class does not implement %s.", ConfigFieldListener.class.getSimpleName()));
                }
                //TODO Replace the following with a method to prevent ArrayIndexOutOfBoundsException
                if (listenerClass.getInterfaces().length > 0) {
                    if (listenerClass.getInterfaces()[0].getGenericInterfaces().length > 0) {
                        listenerClassInterfaceClass = listenerClass.getInterfaces()[0].getGenericInterfaces()[0];
                    }
                }
                Type[] listenerClassInterfaceClassTypes = ((ParameterizedType) listenerClassInterfaceClass).getActualTypeArguments();

                //TODO Replace with smarter way to find the non-config parameter index.
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

                    ConfigFieldListener<T, ?> listener = (ConfigFieldListener<T, ?>) listenerClass.newInstance();

                    listeners.put(field, listener);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new ConfigServiceBuildException("Could not create new instance of class, make sure default no-args constructor exists if args constructor exists.", e);
                }
            }
            return listeners;
        }
    }

}
