package com.jsonmack.worldteleport;

import com.jsonmack.worldteleport.namespaced.TeleportModuleLocatorNamedspacedKey;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Jason MK on 2020-05-24 at 1:55 p.m.
 */
public class TeleportModuleLocatorListener implements Listener {

    private final TeleportModulePlugin plugin;

    public TeleportModuleLocatorListener(TeleportModulePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void on(PrepareItemEnchantEvent event) {
        System.out.println("prepared");
        if (event.getItem().getType() != Material.DIAMOND) {
            System.out.println("what? " + event.getItem().getType());
            return;
        }
        ItemStack stack = event.getItem();

        if (stack.getEnchantments().keySet().stream().anyMatch(e -> e.getName().startsWith("Module Locator"))) {
            System.out.println("stack contains: " + stack.getEnchantments());
            return;
        }
        System.out.println("a");
        Enchantment tier1 = Enchantment.getByKey(plugin.getNamespacedKeySet().get(TeleportModuleLocatorNamedspacedKey.TIER_ONE));

        Enchantment tier2 = Enchantment.getByKey(plugin.getNamespacedKeySet().get(TeleportModuleLocatorNamedspacedKey.TIER_TWO));

        Enchantment tier3 = Enchantment.getByKey(plugin.getNamespacedKeySet().get(TeleportModuleLocatorNamedspacedKey.TIER_THREE));

        Enchantment tier4 = Enchantment.getByKey(plugin.getNamespacedKeySet().get(TeleportModuleLocatorNamedspacedKey.TIER_FOUR));

        Enchantment tier5 = Enchantment.getByKey(plugin.getNamespacedKeySet().get(TeleportModuleLocatorNamedspacedKey.TIER_FIVE));

        System.out.println("b");
        EnchantmentOffer[] offers = event.getOffers();

        System.out.println("c");

        offers[0] = new EnchantmentOffer(tier5, tier5.getStartLevel(), 1);
        offers[1] = new EnchantmentOffer(tier5, tier5.getStartLevel(), 2);
        offers[2] = new EnchantmentOffer(tier5, tier5.getStartLevel(), 3);


        System.out.println("d");
        event.getEnchanter().updateInventory();
        event.setCancelled(false);

        System.out.println("e");
        EnchantingInventory enchantingInventory = (EnchantingInventory) event.getView().getTopInventory();
    }
}
