package com.jsonmack.mcplugins.silktouch_spawner.listener;

import com.jsonmack.mcplugins.silktouch_spawner.SilkTouchSpawnerPlugin;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKey;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKeySet;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Created by Jason MK on 2020-04-15 at 4:40 p.m.
 */
public class SpawnerReductionEvent implements Listener {

    private final SilkTouchSpawnerPlugin plugin;

    public SpawnerReductionEvent(SilkTouchSpawnerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(SpawnerSpawnEvent event) {
        CreatureSpawner spawner = event.getSpawner();

        PersistentDataContainer container = spawner.getPersistentDataContainer();

        SpawnerNamespacedKeySet namespacedKeySet = plugin.getSpawnerNamespacedKeySet();

        NamespacedKey remainingKey = namespacedKeySet.getNamespacedKey(SpawnerNamespacedKey.SPAWNS_REMAINING);

        System.out.println("A");
        if (!container.has(remainingKey, PersistentDataType.INTEGER)) {
            System.out.println("B");
            return;
        }
        int remaining = container.getOrDefault(remainingKey, PersistentDataType.INTEGER, 0);

        if (remaining == -1) {
            //TODO handle maybe?
            System.out.println("C");
            return;
        }
        remaining--;

        if (remaining <= 0) {
            Block block = spawner.getBlock();

            block.breakNaturally();
            System.out.println("broken!");
        }
        container.set(remainingKey, PersistentDataType.INTEGER, remaining);
    }
}
