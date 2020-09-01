package org.jsonmack.chestowner.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jsonmack.chestowner.service.OwnedChestService;

public class ChestPlaceListener implements Listener {

    private final OwnedChestService service;

    public ChestPlaceListener(OwnedChestService service) {
        this.service = service;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        ItemStack item = event.getItemInHand();

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }
        if (!meta.hasDisplayName()) {
            return;
        }
        String displayName = meta.getDisplayName();

        //TODO add way to add support for custom formatting

        String[] arguments = displayName.contains("-")
                ? displayName.split("-") : displayName.split(" ");

        if (arguments.length < 2) {
            return;
        }
        String playersName = arguments[0];

        if (!playersName.equals(event.getPlayer().getName())) {
            return;
        }

    }

}
