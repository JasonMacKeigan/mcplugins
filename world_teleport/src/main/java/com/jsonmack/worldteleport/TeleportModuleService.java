package com.jsonmack.worldteleport;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.*;

/**
 * Created by Jason MK on 2020-04-07 at 6:20 p.m.
 */
public class TeleportModuleService implements ConfigurationSerializable {

    private final List<TeleportModule> modules;

    private final Map<Material, TeleportModule> materialKeys = new HashMap<>();

    public TeleportModuleService(List<TeleportModule> modules) {
        this.modules = modules;
        modules.forEach(module -> materialKeys.put(module.getTeleportLocation().getMaterial(), module));
    }

    @SuppressWarnings("unchecked")
    public TeleportModuleService(Map<String, Object> values) {
        this((List<TeleportModule>) values.get("modules"));
    }

    public void add(TeleportModule module) {
        modules.add(module);
        materialKeys.put(module.getTeleportLocation().getMaterial(), module);
    }

    public void remove(TeleportModule module) {
        modules.remove(module);
        materialKeys.remove(module.getTeleportLocation().getMaterial());
    }

    public Map<Material, TeleportModule> getMaterialKeys() {
        return materialKeys;
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
