package com.jsonmack.mcplugins.harvestxp.listener;

import com.google.common.collect.ImmutableSet;
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

    private final Map<UUID, HarvestService> harvestService = new HashMap<>();

    private final Set<HarvestMaterialConfig> configs;

    private final Map<Material, HarvestMaterialConfig> configsByMaterial;

    public HarvestBlockListener(JavaPlugin plugin, Set<HarvestMaterialConfig> configs) {
        this.plugin = plugin;
        this.configs = configs;
        this.configsByMaterial = configs.stream().collect(Collectors.toMap(HarvestMaterialConfig::getMaterial,
                Function.identity()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (!plugin.isEnabled()) {
            return;
        }
        Block block = event.getBlock();

        Player player = event.getPlayer();

        HarvestMaterialConfig config = configsByMaterial.get(block.getType());

        if (config == null) {
            return;
        }
        HarvestService service = harvestService.computeIfAbsent(player.getUniqueId(), uuid -> new HarvestService());

        service.harvest(block, player, config.getAmountRequired(), config.getExperience());
    }

}
