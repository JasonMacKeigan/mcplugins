package com.jsonmack.mcplugins.harvestxp.listener;

import com.google.common.collect.ImmutableSet;
import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestToolConfig;
import com.jsonmack.mcplugins.harvestxp.harvest.HarvestService;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static org.bukkit.Material.*;

/**
 * Created by Jason MK on 2020-03-13 at 1:51 p.m.
 */
public class HarvestBlockListener implements Listener {

    private final JavaPlugin plugin;

    private final HarvestConfig config;

    private final Map<UUID, HarvestService> harvestService = new HashMap<>();

    private static final Set<Material> HOE_TOOLS = ImmutableSet.of(WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE, DIAMOND_HOE);

    public HarvestBlockListener(JavaPlugin plugin, HarvestConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (!plugin.isEnabled()) {
            return;
        }
        Block block = event.getBlock();

        BlockData blockData = block.getBlockData();

        if (blockData.getClass() != Ageable.class) {
            return;
        }
        Ageable ageable = (Ageable) blockData;

        if (ageable.getAge() < ageable.getMaximumAge()) {
            return;
        }

        Player player = event.getPlayer();

        HarvestMaterialConfig config = this.config.getMaterialConfigsByMaterial().get(block.getType());

        if (config == null) {
            return;
        }
        EntityEquipment equipment = player.getEquipment();

        int reduction = 0;

        if (equipment != null) {
            ItemStack mainHandItem = equipment.getItemInMainHand();

            Material mainHandMaterial = equipment.getItemInMainHand().getType();

            if (this.config.isHoeToolRequired() && !HOE_TOOLS.contains(mainHandMaterial)) {
                return;
            }
            HarvestToolConfig toolConfig = this.config.getToolConfigsByMaterial().get(mainHandItem.getType());

            if (toolConfig != null) {
                reduction = toolConfig.getReduction();
            }
        }
        HarvestService service = harvestService.computeIfAbsent(player.getUniqueId(), uuid -> new HarvestService());

        int amountRequired = Math.max(1, config.getAmountRequired() - reduction);

        service.harvest(block, player, amountRequired, config.getExperience());
    }

}
