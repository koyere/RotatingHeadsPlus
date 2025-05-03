package com.me.koyere.rotatingheadsplus.animation;

/**
 * Sets the entity's yaw to a fixed value without animation.
 */
public class StaticRotation extends HeadAnimation {

    private final float yaw;

    /**
     * @param yaw Fixed yaw angle (in degrees)
     */
    public StaticRotation(float yaw) {
        super("static", Integer.MAX_VALUE); // practically disables repeated ticks
        this.yaw = yaw;
    }

    @Override
    public void tick(Rotatable target) {
        target.setCurrentYaw(yaw);
        target.rotate();
    }
}

