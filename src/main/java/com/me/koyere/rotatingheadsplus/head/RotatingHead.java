package com.me.koyere.rotatingheadsplus.head;

import com.me.koyere.rotatingheadsplus.animation.Rotatable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;

import java.util.UUID;

/**
 * Represents a block-based player head that can be rotated in place.
 */
public class RotatingHead implements Rotatable {

    private final UUID uuid;
    private final String worldName;
    private final int x, y, z;
    private float currentYaw;
    private Location lastLocation;

    public RotatingHead(Location location) {
        this.uuid = UUID.randomUUID();
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.currentYaw = location.getYaw();
        this.lastLocation = location.clone();
    }

    /**
     * Gets the exact block location of this head.
     */
    @Override
    public Location getLocation() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;
        return new Location(world, x, y, z);
    }

    /**
     * Gets the block associated with this rotating head.
     */
    public Block getBlock() {
        Location loc = getLocation();
        if (loc == null) return null;
        return loc.getBlock();
    }

    /**
     * Verifies that the block is still a valid player head.
     */
    @Override
    public boolean isValid() {
        Block block = getBlock();
        if (block == null || block.getType() != Material.PLAYER_HEAD) return false;

        BlockState state = block.getState();
        return state instanceof Skull;
    }

    @Override
    public void setCurrentYaw(float yaw) {
        this.currentYaw = yaw;
    }

    @Override
    public float getCurrentYaw() {
        return currentYaw;
    }

    @Override
    public UUID getUniqueId() {
        return uuid;
    }

    /**
     * Updates internal location data with the latest yaw.
     */
    @Override
    public void rotate() {
        Location loc = getLocation();
        if (loc != null) {
            loc.setYaw(currentYaw);
            lastLocation = loc.clone();
        }
    }
}
