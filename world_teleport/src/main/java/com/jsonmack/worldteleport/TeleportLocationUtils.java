package com.jsonmack.worldteleport;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Jason MK on 2020-04-10 at 1:12 p.m.
 */
public final class TeleportLocationUtils {

    private TeleportLocationUtils() {
        throw new AssertionError("Not permitted to create static-factory class instance.");
    }

    public static boolean isBlocked(World world, TeleportModule module) {
        Location centerLocation = module.getTeleportLocation().getLocation().clone();

        Set<Block> above = LocationUtils.findSurrounding(world, centerLocation, 1, 1, 1)
                .stream()
                .filter(b -> b.getLocation().getY() >= centerLocation.getY())
                .filter(b -> !b.getLocation().equals(centerLocation))
                .collect(Collectors.toSet());

        return above.stream().anyMatch(block -> !block.isEmpty());
    }
}
