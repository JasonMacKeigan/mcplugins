package org.jsonmack.chestowner;

public class OwnedChestLocation {

    private final float x;

    private final float y;

    private final float z;

    private final String world;

    public OwnedChestLocation(float x, float y, float z, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
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
}
