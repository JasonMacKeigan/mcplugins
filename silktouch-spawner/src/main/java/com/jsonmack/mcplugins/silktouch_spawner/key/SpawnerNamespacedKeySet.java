package com.jsonmack.mcplugins.silktouch_spawner.key;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Jason MK on 2020-03-22 at 2:30 a.m.
 */
public class SpawnerNamespacedKeySet {

    private final JavaPlugin plugin;

    private final Map<SpawnerNamespacedKey, NamespacedKey> keys;

    public SpawnerNamespacedKeySet(JavaPlugin plugin) {
        this.plugin = plugin;
        this.keys = Stream.of(SpawnerNamespacedKey.values()).collect(Collectors.toMap(Function.identity(), key -> new NamespacedKey(plugin, key.name())));
    }

    public NamespacedKey getNamespacedKey(SpawnerNamespacedKey key) {
        return keys.get(key);
    }

}
