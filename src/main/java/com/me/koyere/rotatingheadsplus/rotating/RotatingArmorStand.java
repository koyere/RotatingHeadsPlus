package com.me.koyere.rotatingheadsplus.rotating;

import com.me.koyere.rotatingheadsplus.compat.CompatProvider;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

/**
 * Clase que representa un ArmorStand rotatable.
 */
public class RotatingArmorStand implements Rotatable {

    private final ArmorStand armorStand;

    /**
     * Crea un nuevo ArmorStand en la ubicación especificada.
     *
     * @param location Ubicación donde aparecerá el ArmorStand.
     */
    public RotatingArmorStand(Location location) {
        World world = location.getWorld();
        if (world == null) {
            throw new IllegalArgumentException("World cannot be null.");
        }

        this.armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        this.armorStand.setVisible(false);
        this.armorStand.setGravity(false);
        this.armorStand.setMarker(true);
        this.armorStand.setSmall(true);
        this.armorStand.setCustomNameVisible(false);
    }

    /**
     * Devuelve la entidad ArmorStand asociada.
     *
     * @return ArmorStand real en el mundo.
     */
    public ArmorStand getHandle() {
        return armorStand;
    }

    @Override
    public void rotate(float yaw, float pitch) {
        CompatProvider.getAdapter().rotateArmorStand(armorStand, yaw, pitch);
    }

    @Override
    public void lookAt(Location target) {
        CompatProvider.getAdapter().lookAt(getLocation(), target, armorStand);
    }

    @Override
    public Location getLocation() {
        return armorStand.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        armorStand.teleport(location);
    }

    /**
     * Elimina el ArmorStand del mundo.
     */
    public void remove() {
        armorStand.remove();
    }
}
