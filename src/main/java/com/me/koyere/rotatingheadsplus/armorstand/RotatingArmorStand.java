package com.me.koyere.rotatingheadsplus.armorstand;

import com.me.koyere.rotatingheadsplus.animation.Rotatable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.UUID;

public class RotatingArmorStand implements Rotatable {

    private final UUID entityId;
    private final String worldName;
    private final Vector position;
    private float currentYaw;

    public RotatingArmorStand(ArmorStand armorStand) {
        this.entityId = armorStand.getUniqueId();
        this.worldName = armorStand.getWorld().getName();
        this.position = armorStand.getLocation().toVector();
        this.currentYaw = armorStand.getLocation().getYaw();
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
        if (entity instanceof ArmorStand armorStand) {
            Location loc = armorStand.getLocation();
            loc.setYaw(currentYaw);
            armorStand.teleport(loc);
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
        return entity instanceof ArmorStand && entity.isValid();
    }

    @Override
    public void rotate() {
        // Optional, handled in setCurrentYaw() via teleport.
    }
}
