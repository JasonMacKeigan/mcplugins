package com.jsonmack.worldteleport;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Jason MK on 2020-04-10 at 12:49 p.m.
 */
public class TeleportToModuleEvent extends BukkitRunnable {

    private final WorldTeleportPlugin plugin;

    private final Player player;

    private final TeleportModule from;

    private final TeleportModule to;

    private final int diamondCost;

    public TeleportToModuleEvent(WorldTeleportPlugin plugin, Player player, TeleportModule from, TeleportModule to, int diamondCost) {
        this.plugin = plugin;
        this.player = player;
        this.from = from;
        this.to = to;
        this.diamondCost = diamondCost;
    }

    @Override
    public void run() {
        if (player == null || !player.isOnline() || player.isDead() || player.getHealth() <= 0) {
            return;
        }
        TeleportModuleService service = plugin.getService();

        if (from == null || service.getModules().stream().noneMatch(module ->
                module.getTeleportLocation().getMaterial().equals(from.getTeleportLocation().getMaterial()))) {
            cancel("The module you used has been broken.");
            return;
        }
        if (to == null || service.getModules().stream().noneMatch(module ->
                module.getTeleportLocation().getMaterial().equals(to.getTeleportLocation().getMaterial()))) {
            cancel("The module you're teleporting to has been broken.");
            return;
        }

        if (TeleportLocationUtils.isBlocked(player.getWorld(), from)) {
            cancel("The module you used is obstructed by a block.");
            return;
        }

        if (TeleportLocationUtils.isBlocked(player.getWorld(), to)) {
            cancel("The module you're teleporting to is obstructed by a block.");
            return;
        }

        if (!player.getInventory().contains(Material.DIAMOND, diamondCost)) {
            cancel(String.format("You need at least %s diamonds to go to this location.", diamondCost));
            return;
        }
        player.playSound(from.getTeleportLocation().getLocation().clone(), Sound.ENTITY_SHULKER_TELEPORT, 1f, 0f);
        player.getInventory().removeItem(new ItemStack(Material.DIAMOND, diamondCost));

        Location teleportLocation = to.getTeleportLocation().getLocation().clone()
                .add(RandomUtils.nextBoolean() ? -.5 : 1.5, 0, RandomUtils.nextBoolean() ? -.5 : 1.5);

        player.teleport(teleportLocation);
        player.sendMessage(String.format("The module consumes %s diamonds.", diamondCost));
    }

    private void cancel(String message) {
        player.sendMessage(message);
        player.spawnParticle(Particle.VILLAGER_ANGRY, from.getTeleportLocation().getLocation().clone().add(.5f, 1, .5f), 3);
    }
}
