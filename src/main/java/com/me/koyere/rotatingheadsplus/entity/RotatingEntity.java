package com.me.koyere.rotatingheadsplus.entity;

import com.me.koyere.rotatingheadsplus.animation.Rotatable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.UUID;

public class RotatingEntity implements Rotatable {

    private final UUID entityId;
    private final String worldName;
    private final Vector position;
    private float currentYaw;

    public RotatingEntity(Entity entity) {
        this.entityId = entity.getUniqueId();
        this.worldName = entity.getWorld().getName();
        this.position = entity.getLocation().toVector();
        this.currentYaw = entity.getLocation().getYaw();
    }

    @Override
    public Location getLocation() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;
        return new Location(world, position.getX(), position.getY(), position.getZ(), currentYaw, 0f);
    }

    @Override
    public void setCurrentYaw(float yaw) {
        this.currentYaw = yaw;
        Entity entity = Bukkit.getEntity(entityId);
        if (entity != null && entity.isValid()) {
            Location loc = entity.getLocation();
            loc.setYaw(currentYaw);
            entity.teleport(loc);
        }
    }

    @Override
    public float getCurrentYaw() {
        return currentYaw;
    }

    @Override
    public UUID getUniqueId() {
        return entityId;
    }

    @Override
    public boolean isValid() {
        Entity entity = Bukkit.getEntity(entityId);
        return entity != null && entity.isValid();
    }

    @Override
    public void rotate() {
        // Optional, handled in setCurrentYaw() via teleport.
    }
}
