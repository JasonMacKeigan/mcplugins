package com.jsonmack.mcplugins.config.command;

import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.field.ConfigField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-03-20 at 11:02 p.m.
 */
class ConfigTabResultGeneratorImpl<T extends Config> implements ConfigTabResultGenerator<T> {

    @Override
    public List<String> results(T config, String[] arguments) {
        List<String> argumentList = new ArrayList<>(Arrays.asList(arguments));

        List<String> results = new ArrayList<>();

        List<Field> fields = Stream.of(config.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(ConfigField.class)).collect(Collectors.toList());

        if (argumentList.size() < 2) {
            fields.forEach(field -> results.add(field.getName()));
        } else {
            Field fieldArgument = fields.stream().filter(field -> argumentList.stream().anyMatch(arg -> arg.equalsIgnoreCase(field.getName()))).findAny().orElse(null);

            if (fieldArgument == null) {
                results.add("<Unknown>");
            } else {
                Class<?> type = fieldArgument.getType();

                if (type.isEnum()) {
                    Object[] elements = type.getEnumConstants();

                    results.add(String.format("<%s>", Stream.of(elements).map(Object::toString).collect(Collectors.joining(", "))));
                } else {
                    results.add(String.format("<%s>", fieldArgument.getType().getSimpleName()));
                }
            }
        }
        return results;
    }
}
