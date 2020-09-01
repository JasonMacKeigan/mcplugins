package org.jsonmack.chestowner.chest;

import java.util.Objects;
import java.util.UUID;

public class OwnedChest {

    private final String name;

    private final UUID uuid;

    private final OwnedChestLocation location;

    private final int hashcode;

    public OwnedChest(String name, UUID uuid, OwnedChestLocation location) {
        this.name = name;
        this.uuid = uuid;
        this.location = location;
        this.hashcode = Objects.hash(name, uuid, location);
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

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OwnedChest) {
            OwnedChest other = (OwnedChest) obj;

            return name.equals(other.name)
                    && uuid.equals(other.uuid)
                    && location.equals(other.location);
        }
        return false;
    }
}
