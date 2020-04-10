package com.jsonmack.worldteleport;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jason MK on 2020-04-08 at 1:45 p.m.
 */
public class LocationUtils {

    public static Set<Block> findSurrounding(World world, Location location, int radiusX, int radiusY, int radiusZ) {
        Preconditions.checkNotNull(world, "World is null");

        Set<Block> blocks = new HashSet<>();

        for (int x = location.getBlockX() - radiusX; x <= location.getBlockX() + radiusX; x++) {
            for (int z = location.getBlockZ() - radiusZ; z <= location.getBlockZ() + radiusZ; z++) {
                for (int y = location.getBlockY() - radiusY; y <= location.getBlockY() + radiusY; y++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static Set<Block> findSurroundingBelow(World world, Location location, int radiusX, int radiusY, int radiusZ) {
        return findSurrounding(world, new Location(world, location.getBlockX(), location.getBlockY() - 1, location.getBlockZ()),
                radiusX, radiusY, radiusZ);
    }

}
