package com.jsonmack.mcplugins.harvestxp.harvest;

import org.bukkit.Material;

/**
 * Created by Jason MK on 2020-03-13 at 2:11 p.m.
 */
public class Harvested {

    private final Material type;

    private final int amount;

    public Harvested(Material type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public Material getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
