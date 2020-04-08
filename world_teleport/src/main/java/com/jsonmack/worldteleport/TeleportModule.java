package com.jsonmack.worldteleport;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason MK on 2020-04-07 at 5:59 p.m.
 */
public class TeleportModule implements ConfigurationSerializable {

    private final TeleportLocation location;

    public TeleportModule(TeleportLocation location) {
        this.location = location;
    }

    public TeleportModule(Map<String, Object> values) {
        this((TeleportLocation) values.get("teleport_location"));
    }

    public TeleportLocation getLocation() {
        return location;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("teleport_location", location);

        return map;
    }
}
