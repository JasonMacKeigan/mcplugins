package com.jsonmack.worldteleport;

import com.jsonmack.worldteleport.config.TeleportModuleConfig;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-04-07 at 9:21 p.m.
 */
public class TeleportModuleInterfaceListener implements Listener {

    private final TeleportModulePlugin plugin;

    public TeleportModuleInterfaceListener(TeleportModulePlugin plugin) {
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

        TeleportModule module = modules.stream().filter(m -> m.getTeleportLocation().getMaterial() == clicked.getType()).findAny().orElse(null);

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
        double distanceFromLocation = player.getLocation().distance(openModule.getTeleportLocation().getLocation());

        if (distanceFromLocation > 6) {
            player.sendMessage("You are too far from the module, move closer.");
            return;
        }
        final int distance = (int) openModule.getTeleportLocation().getLocation().distance(module.getTeleportLocation().getLocation());

        final int diamondCost = Math.max(1, distance / plugin.getTeleportModuleConfig().getTilesPerDiamond());

        if (!player.getInventory().contains(Material.DIAMOND, diamondCost)) {
            player.sendMessage(String.format("You need at least %s diamond%s to teleport to this module.", diamondCost, diamondCost > 1 ? "s" : ""));
            event.setCancelled(true);
            return;
        }

        if (TeleportLocationUtils.isBlocked(player.getWorld(), module)) {
            player.sendMessage("That teleport is not working, there are blocks on top of it.");
            event.setCancelled(true);
            return;
        }
        TeleportModuleCooldownService cooldownService = plugin.getCooldownService();

        TeleportModuleConfig config = plugin.getTeleportModuleConfig();

        if (config.isCooldownEnabled() && config.getCooldownDuration() > 0 && cooldownService.isOnCooldown(player.getUniqueId())) {
            long difference = TimeUnit.NANOSECONDS.convert(config.getCooldownDuration(), config.getCooldownUnit())
                    - cooldownService.cooldownRemainingNano(player.getUniqueId());

            Duration durationRemaining = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusNanos(difference));

            player.sendMessage(String.format("You teleported recently, please wait %s.",
                    DurationFormatUtils.formatDurationWords(durationRemaining.toMillis(), true, false)));
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
        player.closeInventory();
        player.getWorld().spawnParticle(Particle.PORTAL, openModule.getTeleportLocation().getLocation().clone().add(.5f, 1, .5f), 50);

        TeleportToModuleEvent teleportEvent = new TeleportToModuleEvent(plugin, player, openModule, module, diamondCost);

        teleportEvent.runTaskLater(plugin, 60);
    }

}
