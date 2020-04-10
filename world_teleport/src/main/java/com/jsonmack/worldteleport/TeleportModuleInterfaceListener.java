package com.jsonmack.worldteleport;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-04-07 at 9:21 p.m.
 */
public class TeleportModuleInterfaceListener implements Listener {

    private final WorldTeleportPlugin plugin;

    public TeleportModuleInterfaceListener(WorldTeleportPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(InventoryDragEvent event) {
        Inventory inventory = event.getView().getTopInventory();

        InventoryHolder holder = inventory.getHolder();

        if (holder != null && holder.getClass() == TeleportInventoryHolder.class) { // might not be able to do with wrapper
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryMoveItemEvent event) {
        Inventory inventory = event.getSource();

        InventoryHolder holder = inventory.getHolder();

        if (holder != null && holder.getClass() == TeleportInventoryHolder.class) { // might not be able to do with wrapper
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();

        HumanEntity entity = event.getWhoClicked();

        if (!(entity instanceof Player)) {
            return;
        }
        Player player = (Player) entity;

        InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder == null || inventoryHolder.getClass() != TeleportInventoryHolder.class) {
            return;
        }
        if (event.getAction() != InventoryAction.PICKUP_ALL) {
            event.setCancelled(true);
            return;
        }
        TeleportInventoryHolder holder = (TeleportInventoryHolder) inventoryHolder;

        ItemStack clicked = event.getCurrentItem() != null ? event.getCurrentItem() : event.getCursor();

        if (clicked == null) {
            event.setCancelled(true);
            return;
        }
        List<TeleportModule> modules = plugin.getService().getModules();

        TeleportModule module = modules.stream().filter(m -> m.getLocation().getMaterial() == clicked.getType()).findAny().orElse(null);

        if (event.getClick() != ClickType.LEFT) {
            event.setCancelled(true);
            return;
        }
        if (module == null) {
            event.setCancelled(true);
            return;
        }
        TeleportModule openModule = holder.getModule();

        if (openModule == null) {
            event.setCancelled(true);
            return;
        }
        if (module == openModule) {
            event.setCancelled(true);
            player.sendMessage("You are at this teleport module and therefore cannot teleport to it.");
            return;
        }
        final Location centerLocation = module.getLocation().getLocation();

        final int distance = (int) openModule.getLocation().getLocation().distance(centerLocation);

        final int diamondCost = Math.max(1, distance / 1_000);

        if (!player.getInventory().contains(Material.DIAMOND, diamondCost)) {
            player.sendMessage(String.format("You need at least %s diamonds to teleport to this module.", diamondCost));
            event.setCancelled(true);
            return;
        }
        Set<Block> above = LocationUtils.findSurrounding(player.getWorld(), centerLocation, 1, 1, 1)
                .stream()
                .filter(b -> b.getLocation().getY() >= centerLocation.getY())
                .filter(b -> !b.getLocation().equals(centerLocation))
                .collect(Collectors.toSet());

        if (above.stream().anyMatch(b -> !b.isEmpty())) {
            player.sendMessage("That teleport is not working, there are blocks on top of it.");
            event.setCancelled(true);
            return;
        }
        player.getInventory().removeItem(new ItemStack(Material.DIAMOND, diamondCost));

        Location teleportLocation = centerLocation.clone().add(RandomUtils.nextBoolean() ? -.5 : 1.5, 0, RandomUtils.nextBoolean() ? -.5 : 1.5);

        player.teleport(teleportLocation);

        player.sendMessage(String.format("The module consumes %s diamonds.", diamondCost));
    }

}
