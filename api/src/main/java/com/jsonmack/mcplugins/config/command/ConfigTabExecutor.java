package com.jsonmack.mcplugins.config.command;

import com.jsonmack.mcplugins.config.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-03-20 at 6:59 p.m.
 */
public final class ConfigTabExecutor<T extends Config> implements TabExecutor {

    private final ConfigService<T> service;

    private final ConfigTabResultGenerator<T> resultGenerator;

    public ConfigTabExecutor(ConfigService<T> service, ConfigTabResultGenerator<T> resultGenerator) {
        this.service = service;
        this.resultGenerator = resultGenerator;
    }

    public ConfigTabExecutor(ConfigService<T> service) {
        this(service, new ConfigTabResultGeneratorImpl<>());
    }

    //TODO instead of type erasure break a config up into individual commands that are aware of type
    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            T config = service.getConfig();

            List<Field> fields = Stream.of(config.getClass().getDeclaredFields())
                    .filter(f -> f.isAnnotationPresent(ConfigField.class))
                    .collect(Collectors.toList());

            sender.sendMessage(String.format("%s%s configuration fields and values.", ChatColor.BOLD, config.getClass().getSimpleName()));
            for (Field field : fields) {
                try {
                    field.setAccessible(true);

                    sender.sendMessage(String.format("%s%s: %s%s", ChatColor.GREEN, field.getName(), ChatColor.BLUE, field.get(config)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    sender.sendMessage(String.format("%sUnable to display %s", ChatColor.RED, field.getName()));
                }
            }
            return true;
        }
        if (args.length < 2) {
            return false;
        }
        String fieldName = args[0];

        String value = args[1];

        ConfigListener<T, Object> listener = (ConfigListener<T, Object>) service.getListenersByFieldName().get(fieldName);

        if (listener == null) {
            sender.sendMessage(String.format("%sUnable to find listener for the field <%s>.", ChatColor.RED, fieldName));
            return false;
        }
        Object parsed = null;

        try {
            parsed = listener.parse(value);
        } catch (ConfigListenerParseException e) {
            sender.sendMessage(String.format("%sUnable to parse input <%s>", ChatColor.RED, value));
        }

        ConfigListenerAcceptableResult result = listener.acceptable(parsed);

        if (!result.isAcceptable()) {
            String message = result.getUnacceptableMessage();

            if (!message.isEmpty()) {
                sender.sendMessage(ChatColor.RED + message);
            }
            return false;
        }
        T config = listener.modify(service.getConfig(), parsed);

        if (config == null) {
            sender.sendMessage(ChatColor.RED + "Something went wrong, config when modified is null.");
            return false;
        }
        service.setConfig(config);
        service.getModifiedListener().onModify(config);
        sender.sendMessage(String.format("%sThe config field %s is now %s.", ChatColor.GREEN, fieldName, value));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return resultGenerator.results(service.getConfig(), args);
    }
}
