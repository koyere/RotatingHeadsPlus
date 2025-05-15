package com.me.koyere.rotatingheadsplus.animation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Encapsula las condiciones necesarias para que una animación se ejecute.
 * Soporta mundo, distancia y rango de horas activas.
 */
public class AnimationCondition {

    private final String requiredWorld;
    private final double minDistance;
    private final double maxDistance;
    private final LocalTime startTime;
    private final LocalTime endTime;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public AnimationCondition(String requiredWorld, double minDistance, double maxDistance,
                              String startTimeStr, String endTimeStr) {
        this.requiredWorld = requiredWorld;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.startTime = parseTime(startTimeStr);
        this.endTime = parseTime(endTimeStr);
    }

    private LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Evalúa si se cumplen todas las condiciones para ejecutar una animación.
     *
     * @param origin La ubicación de la entidad o head animada.
     * @return true si se cumplen todas las condiciones.
     */
    public boolean shouldRun(Location origin) {
        // Condición: mundo
        if (requiredWorld != null && !origin.getWorld().getName().equals(requiredWorld)) {
            return false;
        }

        // Condición: distancia
        boolean nearPlayer = false;
        List<Player> players = origin.getWorld().getPlayers();
        for (Player player : players) {
            double distance = player.getLocation().distance(origin);
            if (distance >= minDistance && distance <= maxDistance) {
                nearPlayer = true;
                break;
            }
        }
        if (!nearPlayer) return false;

        // Condición: rango horario
        if (startTime != null && endTime != null) {
            LocalTime now = LocalTime.now();
            boolean inRange = startTime.isBefore(endTime)
                    ? now.isAfter(startTime) && now.isBefore(endTime)
                    : now.isAfter(startTime) || now.isBefore(endTime); // para rangos que cruzan medianoche
            if (!inRange) return false;
        }

        return true;
    }

    // === Getters ===

    public String getRequiredWorld() {
        return requiredWorld;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
