package com.me.koyere.rotatingheadsplus.rotating;

import org.bukkit.Location;

/**
 * Interfaz que define un objeto rotatable con una posición en el mundo.
 * Las clases como RotatingHead, RotatingArmorStand o RotatingEntity deben implementarla.
 */
public interface Rotatable {

    /**
     * Rota la entidad virtual en base a los valores de yaw y pitch dados.
     *
     * @param yaw   Rotación horizontal en grados.
     * @param pitch Rotación vertical en grados.
     */
    void rotate(float yaw, float pitch);

    /**
     * Hace que esta entidad mire hacia la ubicación objetivo.
     *
     * @param target Ubicación objetivo.
     */
    void lookAt(Location target);

    /**
     * Obtiene la ubicación actual de la entidad rotatable.
     *
     * @return Ubicación actual.
     */
    Location getLocation();

    /**
     * Establece la ubicación actual de la entidad.
     * Esto se utiliza para actualizar el estado interno después de una rotación.
     *
     * @param location Nueva ubicación.
     */
    void setLocation(Location location);
}
