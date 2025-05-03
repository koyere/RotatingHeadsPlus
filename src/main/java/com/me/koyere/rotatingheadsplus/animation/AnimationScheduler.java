package com.me.koyere.rotatingheadsplus.animation;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Controls the animation loop for all Rotatable entities.
 */
public class AnimationScheduler {

    // Map of entity UUID to its assigned animation
    private final Map<UUID, HeadAnimation> animations = new HashMap<>();

    // Map of entity UUID to the actual Rotatable object (head, armorstand, etc.)
    private final Map<UUID, Rotatable> entities = new HashMap<>();

    private int taskId = -1;
    private int tickCounter = 0;

    /**
     * Starts the repeating task that animates all registered entities.
     */
    public void start() {
        if (taskId != -1) return; // Already running

        taskId = new BukkitRunnable() {
            @Override
            public void run() {
                tickCounter++;

                for (UUID id : animations.keySet()) {
                    Rotatable rotatable = entities.get(id);
                    HeadAnimation animation = animations.get(id);

                    if (rotatable == null || animation == null || !rotatable.isValid()) continue;

                    if (tickCounter % animation.getTickInterval() == 0) {
                        animation.tick(rotatable);
                    }
                }
            }
        }.runTaskTimer(RotatingHeadsPlus.getInstance(), 1L, 1L).getTaskId();
    }

    /**
     * Stops the animation loop.
     */
    public void stop() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }

    /**
     * Assigns an animation to a given Rotatable object.
     */
    public void assignAnimation(Rotatable target, HeadAnimation animation) {
        UUID id = target.getUniqueId();
        animations.put(id, animation);
        entities.put(id, target);
    }

    /**
     * Removes the animation and tracking of a Rotatable object.
     */
    public void removeAnimation(Rotatable target) {
        UUID id = target.getUniqueId();
        animations.remove(id);
        entities.remove(id);
    }

    /**
     * Clears all animations and registered entities.
     */
    public void clearAll() {
        animations.clear();
        entities.clear();
    }

    /**
     * Returns all UUIDs currently being animated.
     */
    public Set<UUID> getAllAnimatedIds() {
        return new HashSet<>(animations.keySet());
    }

    /**
     * Returns the Rotatable object associated with a UUID.
     */
    public Rotatable getRotatableById(UUID id) {
        return entities.get(id);
    }

    /**
     * Gets the animation assigned to a specific Rotatable.
     */
    public HeadAnimation getAnimation(Rotatable target) {
        return animations.get(target.getUniqueId());
    }
}
