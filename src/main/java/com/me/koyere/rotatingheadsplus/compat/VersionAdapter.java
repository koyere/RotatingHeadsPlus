package com.me.koyere.rotatingheadsplus.compat;

import org.bukkit.entity.ArmorStand;
import org.bukkit.Location;

/**
 * Interfaz común para adaptadores de versión.
 * Permite manejar rotaciones y comportamiento de entidades
 * de forma compatible con múltiples versiones de Minecraft.
 */
public interface VersionAdapter {

    /**
     * Rota un ArmorStand a los valores especificados.
     *
     * @param armorStand ArmorStand objetivo.
     * @param yaw Rotación horizontal.
     * @param pitch Rotación vertical.
     */
    void rotateArmorStand(ArmorStand armorStand, float yaw, float pitch);

    /**
     * Hace que un ArmorStand mire hacia un objetivo.
     *
     * @param origin Posición del ArmorStand.
     * @param target Posición objetivo a mirar.
     * @param armorStand ArmorStand que se va a rotar.
     */
    void lookAt(Location origin, Location target, ArmorStand armorStand);

    /**
     * Genera un ID de entidad aleatorio para uso en paquetes.
     *
     * @return ID de entidad único.
     */
    int generateEntityId();
}
