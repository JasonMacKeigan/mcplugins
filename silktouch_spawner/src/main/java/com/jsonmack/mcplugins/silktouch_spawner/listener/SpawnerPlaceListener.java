package com.jsonmack.mcplugins.silktouch_spawner.listener;

import com.jsonmack.mcplugins.silktouch_spawner.SilkTouchSpawnerPlugin;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKey;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKeySet;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Created by Jason MK on 2020-03-22 at 2:37 a.m.
 */
public class SpawnerPlaceListener implements Listener {

    private final SilkTouchSpawnerPlugin plugin;

    public SpawnerPlaceListener(SilkTouchSpawnerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.SPAWNER) {
            return;
        }
        ItemStack itemInHand = event.getItemInHand();

        ItemMeta meta = itemInHand.getItemMeta();

        if (meta == null) {
            //TODO something went wrong, handle this edge-case
            throw new IllegalStateException("This shouldn't happen.");
        }
        SpawnerNamespacedKeySet keySet = plugin.getSpawnerNamespacedKeySet();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        NamespacedKey typeKey = keySet.getNamespacedKey(SpawnerNamespacedKey.TYPE);

        NamespacedKey remainingAmount = keySet.getNamespacedKey(SpawnerNamespacedKey.SPAWNS_REMAINING);

        if (container.has(typeKey, PersistentDataType.STRING)) {
            String type = container.get(typeKey, PersistentDataType.STRING);

            block.setMetadata("type", new FixedMetadataValue(plugin, type));
            block.setMetadata("amountOfSpawns", new FixedMetadataValue(plugin, container.get(remainingAmount, PersistentDataType.INTEGER)));

            CreatureSpawner spawner = (CreatureSpawner) block.getState();

            spawner.setSpawnedType(EntityType.valueOf(type));
            spawner.update(false);
            return;
        }
        String type = container.get(keySet.getNamespacedKey(SpawnerNamespacedKey.TYPE), PersistentDataType.STRING);

        EntityType entityType = EntityType.valueOf(type);

        BlockState blockState = block.getState();

        if (!(blockState instanceof CreatureSpawner)) {
            throw new IllegalStateException("block is not a creature spawner");
        }
        CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;

        creatureSpawner.setSpawnedType(entityType);
        creatureSpawner.update(false);
    }

}
