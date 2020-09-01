package org.jsonmack.chestowner;

import org.bukkit.plugin.java.JavaPlugin;

public class ChestOwnerPlugin extends JavaPlugin {

    // > Creating a tag with the players name in the format of "%s's chest" where %s is the players in-game name
    //      will only allow that chest to be opened by the player.
    // > When placing the chest, check if it has a tag and if so, update data file to include name, uuid, and location
    // > A player can only put a locked chest down if they're the owner of the chest when placing, otherwise they cant.
    // > It cannot be broken by anyone but the owner.
    // > Two large chests cannot be placed side by side there must be a block between to ensure areas that are inpentrable
    //      are not created


    @Override
    public void onLoad() {
        super.onLoad();
    }
}
