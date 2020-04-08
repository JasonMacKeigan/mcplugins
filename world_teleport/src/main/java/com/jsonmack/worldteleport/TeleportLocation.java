package com.jsonmack.worldteleport;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason MK on 2020-04-07 at 5:56 p.m.
 */
public class TeleportLocation implements ConfigurationSerializable {

    private final Location location;

    private final Material centerMaterial;

    public TeleportLocation(Location location, Material centerMaterial) {
        this.location = location;
        this.centerMaterial = centerMaterial;
    }

    public TeleportLocation(Map<String, Object> values) {
        this((Location) values.get("location"),
                Material.getMaterial((String) values.get("material")));
    }

    public Location getLocation() {
        return location;
    }

    public Material getMaterial() {
        return centerMaterial;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("location", location);
        map.put("material", centerMaterial.name());

        return map;
    }
}
