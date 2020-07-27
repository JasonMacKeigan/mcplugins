package com.jsonmack.worldteleport;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Jason MK on 2020-05-24 at 12:32 p.m.
 */
public class TeleportModuleLocatorEnchantment extends Enchantment {

    private final String name;

    private final int minimumLevel;

    private final int maximumLevel;

    public TeleportModuleLocatorEnchantment(NamespacedKey key, String name, int minimumLevel, int maximumLevel) {
        super(key);
        this.name = name;
        this.minimumLevel = minimumLevel;
        this.maximumLevel = maximumLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maximumLevel;
    }

    @Override
    public int getStartLevel() {
        return minimumLevel;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }
}
