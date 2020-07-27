package com.jsonmack.mcplugins.namespaced;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-03-22 at 2:30 a.m.
 */
public class NamespacedKeySet {

    private final JavaPlugin plugin;

    private final Map<String, NamespacedKey> keys;

    public NamespacedKeySet(JavaPlugin plugin, Collection<NamespacedKeyProvider> providers) {
        this.plugin = plugin;
        this.keys = providers.stream()
                .collect(Collectors.toMap(NamespacedKeyProvider::uniqueIdentifier,
                        key -> new NamespacedKey(plugin, key.uniqueIdentifier())));
    }

    public NamespacedKey get(NamespacedKeyProvider provider) {
        return keys.get(provider.uniqueIdentifier());
    }

}
