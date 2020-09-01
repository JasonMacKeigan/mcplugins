package org.jsonmack.chestowner.chest;

import java.util.Objects;

public class OwnedChestLocation {

    private final float x;

    private final float y;

    private final float z;

    private final String world;

    private final int hashcode;

    public OwnedChestLocation(float x, float y, float z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
        this.hashcode = Objects.hash(x, y, z, world);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String getWorld() {
        return world;
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
        if (obj instanceof OwnedChestLocation) {
            OwnedChestLocation other = (OwnedChestLocation) obj;

            return x == other.x && y == other.y && z == other.z
                    && world.equals(other.world);
        }
        return false;
    }
}
