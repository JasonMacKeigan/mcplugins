package com.jsonmack.worldteleport;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jason MK on 2020-04-07 at 6:20 p.m.
 */
public class TeleportModuleService implements ConfigurationSerializable {

    private final List<TeleportModule> modules;

    public TeleportModuleService(List<TeleportModule> modules) {
        this.modules = modules;
    }

    public TeleportModuleService(Map<String, Object> values) {
        this((List<TeleportModule>) values.get("modules"));
    }

    public void add(TeleportModule module) {
        modules.add(module);
    }

    public void remove(TeleportModule module) {
        modules.remove(module);
    }

    public List<TeleportModule> getModules() {
        return modules;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();

        map.put("modules", modules);

        return map;
    }
}
