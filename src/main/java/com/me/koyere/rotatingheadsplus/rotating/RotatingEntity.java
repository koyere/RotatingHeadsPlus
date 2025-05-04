package com.me.koyere.rotatingheadsplus.rotating;

import com.me.koyere.rotatingheadsplus.compat.RotationUtils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

/**
 * Clase que representa una entidad viva rotatable.
 */
public class RotatingEntity implements Rotatable {

    private final LivingEntity entity;

    /**
     * Crea un envoltorio rotatable para una entidad viva.
     *
     * @param entity Entidad que se desea rotar.
     */
    public RotatingEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    @Override
    public void rotate(float yaw, float pitch) {
        Location loc = entity.getLocation();
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        entity.teleport(loc);
    }

    @Override
    public void lookAt(Location target) {
        Vector yawPitch = RotationUtils.getYawPitch(getLocation(), target);
        float yaw = (float) yawPitch.getX();
        float pitch = (float) yawPitch.getY();
        rotate(yaw, pitch);
    }

    @Override
    public Location getLocation() {
        return entity.getLocation();
    }

    @Override
    public void setLocation(Location location) {
        entity.teleport(location);
    }

    public void remove() {
        entity.remove();
    }
}
