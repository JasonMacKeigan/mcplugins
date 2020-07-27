package com.jsonmack.worldteleport.namespaced;

import com.jsonmack.mcplugins.namespaced.NamespacedKeyProvider;

/**
 * Created by Jason MK on 2020-05-24 at 12:40 p.m.
 */
public enum TeleportModuleLocatorNamedspacedKey implements NamespacedKeyProvider {
    TIER_ONE,

    TIER_TWO,

    TIER_THREE,

    TIER_FOUR,

    TIER_FIVE
    ;

    @Override
    public String uniqueIdentifier() {
        return name();
    }
}
