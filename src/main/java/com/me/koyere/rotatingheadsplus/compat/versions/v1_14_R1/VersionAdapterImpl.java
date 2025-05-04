package com.me.koyere.rotatingheadsplus.compat.versions.v1_14_R1;

import com.me.koyere.rotatingheadsplus.compat.VersionAdapter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.Random;

/**
 * Implementación segura de VersionAdapter para Minecraft 1.14
 * usando solo la API de Bukkit/Spigot.
 */
public class VersionAdapterImpl implements VersionAdapter {

    @Override
    public void rotateArmorStand(ArmorStand armorStand, float yaw, float pitch) {
        Location location = armorStand.getLocation();
        location.setYaw(yaw);
        location.setPitch(pitch);
        armorStand.teleport(location);
    }

    @Override
    public void lookAt(Location origin, Location target, ArmorStand armorStand) {
        Location diff = target.clone().subtract(origin);
        double dx = diff.getX();
        double dy = diff.getY();
        double dz = diff.getZ();

        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

        rotateArmorStand(armorStand, yaw, pitch);
    }

    @Override
    public int generateEntityId() {
        return new Random().nextInt(Integer.MAX_VALUE);
    }
}

