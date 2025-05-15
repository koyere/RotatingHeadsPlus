package com.me.koyere.rotatingheadsplus.api;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.animation.AnimationScheduler;
import com.me.koyere.rotatingheadsplus.animation.HeadAnimation;
import com.me.koyere.rotatingheadsplus.head.HeadManager;
import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import com.me.koyere.rotatingheadsplus.armorstand.RotatingArmorStand;
import com.me.koyere.rotatingheadsplus.entity.RotatingEntity;
import com.me.koyere.rotatingheadsplus.animation.Rotatable;

import java.util.UUID;

/**
 * Public API for RotatingHeadsPlus.
 * Allows external plugins to register animations, retrieve objects, and interact with the rotation system.
 */
public class RotatingHeadsAPI {

    private static final RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

    /**
     * Returns the global AnimationScheduler instance.
     */
    public static AnimationScheduler getAnimationScheduler() {
        return plugin.getAnimationScheduler();
    }

    /**
     * Returns the HeadManager instance (only handles RotatingHead).
     */
    public static HeadManager getHeadManager() {
        return plugin.getHeadManager();
    }

    /**
     * Registers a new animation for a Rotatable (head, armorstand, or entity).
     */
    public static void registerAnimation(Rotatable obj, HeadAnimation animation) {
        plugin.getAnimationScheduler().assignAnimation(obj, animation);
    }

    /**
     * Removes the animation assigned to a given Rotatable object.
     */
    public static void removeAnimation(Rotatable obj) {
        plugin.getAnimationScheduler().removeAnimation(obj);
    }

    /**
     * Returns the Rotatable instance by UUID (if currently animated).
     */
    public static Rotatable getRotatable(UUID id) {
        return plugin.getAnimationScheduler().getRotatableById(id);
    }

    /**
     * Gets the current animation assigned to a Rotatable.
     */
    public static HeadAnimation getAnimation(Rotatable obj) {
        return plugin.getAnimationScheduler().getAnimation(obj);
    }
}
