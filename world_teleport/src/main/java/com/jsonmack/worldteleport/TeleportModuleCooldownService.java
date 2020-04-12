package com.jsonmack.worldteleport;

import com.jsonmack.worldteleport.config.TeleportModuleConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason MK on 2020-04-11 at 7:02 p.m.
 */
public class TeleportModuleCooldownService {

    private final TeleportModulePlugin plugin;

    private final Map<UUID, Long> cooldownTimestamps = new HashMap<>();

    public TeleportModuleCooldownService(TeleportModulePlugin plugin) {
        this.plugin = plugin;
    }

    public void cooldown(UUID player) {
        cooldownTimestamps.put(player, System.nanoTime());
    }

    public boolean isOnCooldown(UUID player) {
        long cooldownTimestamp = cooldownTimestamps.getOrDefault(player, 0L);

        if (cooldownTimestamp == 0L) {
            return false;
        }
        TeleportModuleConfig config = plugin.getTeleportModuleConfig();

        return System.nanoTime() - cooldownTimestamp < TimeUnit.NANOSECONDS.convert(config.getCooldownDuration(), config.getCooldownUnit());
    }

    public void clear() {
        cooldownTimestamps.clear();
    }

    public void removeIfExists(UUID player) {
        cooldownTimestamps.remove(player);
    }

    public long cooldownRemainingNano(UUID player) {
        return System.nanoTime() - cooldownTimestamps.getOrDefault(player, 0L);
    }

}
