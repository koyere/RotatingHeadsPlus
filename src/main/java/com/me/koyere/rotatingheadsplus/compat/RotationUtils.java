package com.me.koyere.rotatingheadsplus.compat;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Clase utilitaria para cálculos de rotación y orientación en el mundo de Minecraft.
 */
public class RotationUtils {

    /**
     * Calcula el yaw necesario para mirar desde una ubicación hacia otra.
     *
     * @param from Ubicación de origen.
     * @param to   Ubicación objetivo.
     * @return Valor de yaw en grados.
     */
    public static float getYawTo(Location from, Location to) {
        Vector direction = to.toVector().subtract(from.toVector());
        return (float) Math.toDegrees(Math.atan2(-direction.getX(), direction.getZ()));
    }

    /**
     * Calcula el pitch necesario para mirar desde una ubicación hacia otra.
     *
     * @param from Ubicación de origen.
     * @param to   Ubicación objetivo.
     * @return Valor de pitch en grados.
     */
    public static float getPitchTo(Location from, Location to) {
        Vector direction = to.toVector().subtract(from.toVector());
        double distanceXZ = Math.sqrt(direction.getX() * direction.getX() + direction.getZ() * direction.getZ());
        return (float) Math.toDegrees(Math.atan2(direction.getY(), distanceXZ));
    }

    /**
     * Calcula ambos ángulos (yaw y pitch) necesarios para mirar hacia otra posición.
     *
     * @param from Ubicación de origen.
     * @param to   Ubicación objetivo.
     * @return Vector con yaw en X y pitch en Y.
     */
    public static Vector getYawPitch(Location from, Location to) {
        float yaw = getYawTo(from, to);
        float pitch = getPitchTo(from, to);
        return new Vector(yaw, pitch, 0);
    }
}
