package com.jsonmack.mcplugins.harvestxp.listener;

import com.google.common.collect.ImmutableSet;
import com.jsonmack.mcplugins.harvestxp.config.HarvestConfig;
import com.jsonmack.mcplugins.harvestxp.config.HarvestMaterialConfig;
import com.jsonmack.mcplugins.harvestxp.harvest.HarvestService;
import com.jsonmack.mcplugins.harvestxp.harvest.Harvested;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.bukkit.Material.*;

/**
 * Created by Jason MK on 2020-03-13 at 1:51 p.m.
 */
public class HarvestBlockListener implements Listener {

    private final JavaPlugin plugin;

    private final HarvestConfig config;

    private final Map<UUID, HarvestService> harvestService = new HashMap<>();

    private static final Set<Material> HOE_TOOLS = ImmutableSet.of(WOOD_HOE, STONE_HOE, IRON_HOE, GOLD_HOE, DIAMOND_HOE);

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

        Player player = event.getPlayer();

        HarvestMaterialConfig config = this.config.getMaterialConfigsByMaterial().get(block.getType());

        if (config == null) {
            return;
        }
        if (this.config.isHoeToolRequired()) {
            ItemStack mainHandItem = player.getEquipment().getItemInMainHand();

            if (mainHandItem == null || !HOE_TOOLS.contains(mainHandItem.getType())) {
                return;
            }
        }
        HarvestService service = harvestService.computeIfAbsent(player.getUniqueId(), uuid -> new HarvestService());

        service.harvest(block, player, config.getAmountRequired(), config.getExperience());
    }

}
