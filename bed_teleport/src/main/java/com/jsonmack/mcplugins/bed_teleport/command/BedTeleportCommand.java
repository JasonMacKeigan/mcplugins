package com.jsonmack.mcplugins.bed_teleport.command;

import com.jsonmack.mcplugins.bed_teleport.BedTeleportCostPlugin;
import com.jsonmack.mcplugins.bed_teleport.config.BedTeleportConfig;
import com.jsonmack.mcplugins.bed_teleport.cost.BedTeleportCostRequirement;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-03-19 at 7:43 p.m.
 */
public class BedTeleportCommand implements CommandExecutor {

    private final BedTeleportCostPlugin plugin;

    private final Map<UUID, LocalDateTime> timestamps = new HashMap<>();

    public BedTeleportCommand(BedTeleportCostPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BedTeleportConfig config = plugin.getBedTeleportConfig();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isDead()) {
                return true;
            }
            if (player.getFireTicks() > 0) {
                player.sendMessage("You are on fire, you cannot use this while on fire.");
                return true;
            }
            if (teleportedRecently(player)) {
                return true;
            }
            World.Environment environment = player.getWorld().getEnvironment();

            if (environment != World.Environment.NORMAL) {
                player.sendMessage(String.format("You cannot go to your bed while in the %s.", environment.name().toLowerCase()));
                return true;
            }
            BedTeleportCostRequirement requirement = config.getCostType().getRequirement();

            if (!requirement.meets(player, config.getCostAmount())) {
                return true;
            }
            Location bedLocation = player.getBedSpawnLocation();

            if (bedLocation == null) {
                player.sendMessage("Odd, you don't seem to have a bed spawned or haven't slept in it yet.");
                return true;
            }
            World bedWorld = bedLocation.getWorld();

            if (bedWorld == null) {
                player.sendMessage("Odd, your bed doesn't exist in a standard world.");
                return true;
            }
            World.Environment bedEnvironment = bedWorld.getEnvironment();

            if (bedEnvironment != World.Environment.NORMAL) {
                player.sendMessage(String.format("Your bed is in the %s so you cannot go to it.", bedEnvironment.name().toLowerCase()));
                return true;
            }
            timestamps.put(player.getUniqueId(), LocalDateTime.now());
            requirement.remove(player, config.getCostAmount());
            player.teleport(bedLocation);
            player.sendMessage(String.format("You have teleported to your bed at the cost of a %sx %s%s.",
                    config.getCostAmount(), config.getCostType().name().toLowerCase(), config.getCostAmount() > 1 ? "s" : ""));
            return true;
        }
        return false;
    }

    private boolean teleportedRecently(Player player) {
        LocalDateTime lastTeleport = timestamps.get(player.getUniqueId());

        if (lastTeleport != null) {
            BedTeleportConfig config = plugin.getBedTeleportConfig();

            long cooldownNanoseconds = config.getCooldownUnit() == TimeUnit.NANOSECONDS ? config.getCooldownDuration()
                    : TimeUnit.NANOSECONDS.convert(config.getCooldownDuration(), config.getCooldownUnit());

            Duration durationBetweenTeleports = Duration.between(lastTeleport, ZonedDateTime.now());

            long remaining = cooldownNanoseconds - durationBetweenTeleports.toNanos();

            if (remaining > 0L) {
                player.sendMessage(String.format("You need to wait %s to do this again.",
                        DurationFormatUtils.formatDurationWords(TimeUnit.NANOSECONDS.toMillis(remaining), true, false)));
                return true;
            }
        }
        return false;
    }
}
