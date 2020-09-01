package org.jsonmack.chestowner;

import java.util.UUID;

public class OwnedChest {

    private final String name;

    private final UUID uuid;

    private final OwnedChestLocation location;

    public OwnedChest(String name, UUID uuid, OwnedChestLocation location) {
        this.name = name;
        this.uuid = uuid;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public OwnedChestLocation getLocation() {
        return location;
    }
}
