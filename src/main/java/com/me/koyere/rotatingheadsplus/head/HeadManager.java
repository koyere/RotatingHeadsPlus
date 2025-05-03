package com.me.koyere.rotatingheadsplus.head;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class HeadManager {

    private final Map<UUID, RotatingHead> heads = new ConcurrentHashMap<>();

    /**
     * Registers a new rotating head into the manager.
     */
    public void registerHead(RotatingHead head) {
        heads.put(head.getUniqueId(), head);
    }

    /**
     * Unregisters a rotating head by its UUID.
     */
    public void unregisterHead(UUID uuid) {
        heads.remove(uuid);
    }

    /**
     * Unregisters a rotating head by object reference.
     */
    public void unregisterHead(RotatingHead head) {
        unregisterHead(head.getUniqueId());
    }

    /**
     * Returns a collection of all active rotating heads.
     */
    public Collection<RotatingHead> getAllHeads() {
        return heads.values();
    }

    /**
     * Retrieves a rotating head by its UUID.
     */
    public RotatingHead getHead(UUID uuid) {
        return heads.get(uuid);
    }

    /**
     * Finds the closest rotating head to a player within a certain range.
     */
    public RotatingHead getNearestHead(Player player, double maxDistance) {
        Location playerLoc = player.getLocation();
        RotatingHead nearest = null;
        double closestDistance = maxDistance;

        for (RotatingHead head : heads.values()) {
            if (!Objects.equals(head.getWorld(), player.getWorld())) continue;

            double distance = head.getLocation().distance(playerLoc);
            if (distance <= closestDistance) {
                closestDistance = distance;
                nearest = head;
            }
        }

        return nearest;
    }

    /**
     * Removes all rotating heads (use with caution).
     */
    public void clearAllHeads() {
        heads.clear();
    }
}
