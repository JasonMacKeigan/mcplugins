package com.jsonmack.mcplugins.silktouch_spawner.listener;

import com.jsonmack.mcplugins.silktouch_spawner.SilkTouchSpawnerPlugin;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKey;
import com.jsonmack.mcplugins.silktouch_spawner.key.SpawnerNamespacedKeySet;
import com.sun.tools.javac.util.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

/**
 * Created by Jason MK on 2020-03-22 at 1:22 a.m.
 */
public class SpawnerBreakListener implements Listener {

    private final SilkTouchSpawnerPlugin plugin;

    public SpawnerBreakListener(SilkTouchSpawnerPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlock();

        if (block.getType() != Material.SPAWNER) {
            return;
        }
        ItemStack mainHand = player.getInventory().getItemInMainHand();

        Material mainHandMaterial = mainHand.getType();

        if (mainHandMaterial != Material.DIAMOND_PICKAXE) {
            return;
        }
        if (!mainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }
        ItemStack spawner = new ItemStack(Material.SPAWNER, 1);

        BlockState blockState = block.getState();

        if (!(blockState instanceof CreatureSpawner)) {
            return;
        }
        CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;

        ItemMeta spawnerMeta = spawner.getItemMeta();

        if (spawnerMeta == null) {
            throw new IllegalStateException("meta is null :/");
        }
        SpawnerNamespacedKeySet keySet = plugin.getSpawnerNamespacedKeySet();

        String spawnName = StringUtils.toUpperCase(creatureSpawner.getSpawnedType().name().toLowerCase());

        System.out.println("meta: " + spawnerMeta);

        spawnerMeta.setLore(Collections.singletonList(String.format("Drop to spawn a %s%s.", ChatColor.GREEN, spawnName)));

        spawnerMeta.setDisplayName(String.format("%s Spawner", spawnName));

        spawnerMeta.getPersistentDataContainer().set(keySet.getNamespacedKey(SpawnerNamespacedKey.TYPE),
                PersistentDataType.STRING, creatureSpawner.getSpawnedType().name());

        spawner.setItemMeta(spawnerMeta);

        player.getWorld().dropItemNaturally(block.getLocation(), spawner);
    }

}
