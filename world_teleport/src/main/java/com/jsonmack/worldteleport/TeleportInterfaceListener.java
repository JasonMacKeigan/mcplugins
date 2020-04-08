package com.jsonmack.worldteleport;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Jason MK on 2020-04-07 at 9:21 p.m.
 */
public class TeleportInterfaceListener implements Listener {

    private final WorldTeleportPlugin plugin;

    public TeleportInterfaceListener(WorldTeleportPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(InventoryDragEvent event) {
        Inventory inventory = event.getView().getTopInventory();

        InventoryHolder holder = inventory.getHolder();

        if (holder instanceof TeleportInventoryHolder) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryMoveItemEvent event) {
        Inventory inventory = event.getSource();

        if (inventory.getHolder() instanceof TeleportInventoryHolder) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryClickEvent event) {
        Inventory inventory = event.getView().getTopInventory();

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();

        if (inventory.getHolder() instanceof TeleportInventoryHolder) {
            if (event.getAction() != InventoryAction.PICKUP_ALL) {
                event.setCancelled(true);
                player.updateInventory();
                return;
            }
            TeleportInventoryHolder holder = (TeleportInventoryHolder) inventory.getHolder();

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
                return;
            }
            int distance = (int) openModule.getLocation().getLocation().distance(module.getLocation().getLocation());

            int diamondCost = Math.max(1, distance / 1_000);

            if (!player.getInventory().contains(Material.DIAMOND, diamondCost)) {
                event.setCancelled(true);
                return;
            }
            player.getInventory().removeItem(new ItemStack(Material.DIAMOND, diamondCost));

            Location centerLocation = module.getLocation().getLocation();

            Location teleportLocation = new Location(centerLocation.getWorld(), centerLocation.getBlockX(), centerLocation.getBlockY(), centerLocation.getBlockZ() - 1);

            player.teleport(teleportLocation);

            player.sendMessage(String.format("The teleport module consumes %s diamonds to add to the foundation.", diamondCost));
        }
    }

}
