package com.jsonmack.worldteleport;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jason MK on 2020-04-07 at 7:47 p.m.
 */
public class TeleportInventoryHolder implements InventoryHolder {

    private final TeleportModule module;

    private final List<TeleportModule> modules;

    private final Inventory inventory;

    private final TeleportModulePlugin plugin;

    public TeleportInventoryHolder(TeleportModulePlugin plugin, TeleportModule module, List<TeleportModule> modules) {
        this.plugin = plugin;
        this.module = module;
        this.modules = modules;
        this.inventory = create(module, modules);
    }

    public TeleportModule getModule() {
        return module;
    }

    public List<TeleportModule> getModules() {
        return modules;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    private Inventory create(TeleportModule currentModule, List<TeleportModule> modules) {
        Inventory inventory = Bukkit.createInventory(this, 9, "Teleport Modules");

        inventory.setMaxStackSize(1);

        for (int index = 0; index < modules.size(); index++) {
            TeleportModule module = modules.get(index);

            Material material = module.getTeleportLocation().getMaterial();

            ItemStack stack = new ItemStack(material, 1);

            int distance = (int) currentModule.getTeleportLocation().getLocation().distance(module.getTeleportLocation().getLocation());

            ItemMeta meta = stack.getItemMeta();

            if (meta != null) {
                meta.setLore(Collections.singletonList(String.format("Cost: %s Diamonds",
                        Math.max(1, Math.min(64, distance / plugin.getTeleportModuleConfig().getTilesPerDiamond())))));
            }
            stack.setItemMeta(meta);

            inventory.setItem(index, stack);
        }
        return inventory;
    }
}
