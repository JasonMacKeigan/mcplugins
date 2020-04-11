package com.jsonmack.worldteleport.config;

import com.jsonmack.mcplugins.config.Config;
import com.jsonmack.mcplugins.config.ConfigField;
import com.jsonmack.worldteleport.config.field.TilesPerDiamondFieldListener;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jason MK on 2020-04-10 at 10:07 p.m.
 */
public class TeleportModuleConfig implements Config, ConfigurationSerializable {

    @ConfigField(value = TilesPerDiamondFieldListener.class)
    private final int tilesPerDiamond;

    public TeleportModuleConfig(int tilesPerDiamond) {
        this.tilesPerDiamond = tilesPerDiamond;
    }

    public TeleportModuleConfig(Map<String, Object> values) {
        this((int) values.get("tiles_per_diamond"));
    }

    public TeleportModuleConfig setTilesPerDiamond(int tilesPerDiamond) {
        return new TeleportModuleConfig(tilesPerDiamond);
    }

    public int getTilesPerDiamond() {
        return tilesPerDiamond;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> values = new HashMap<>();

        values.put("tiles_per_diamond", tilesPerDiamond);

        return values;
    }
}
