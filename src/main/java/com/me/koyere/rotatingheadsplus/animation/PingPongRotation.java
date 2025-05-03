package com.me.koyere.rotatingheadsplus.animation;

/**
 * Rotates the entity back and forth between two yaw angles.
 */
public class PingPongRotation extends HeadAnimation {

    private final float minYaw;
    private final float maxYaw;
    private final float step;
    private boolean forward = true;

    /**
     * @param minYaw Minimum yaw angle (e.g. -45f)
     * @param maxYaw Maximum yaw angle (e.g. 45f)
     * @param step How much to adjust per tick
     * @param tickInterval How often to update (1 = every tick)
     */
    public PingPongRotation(float minYaw, float maxYaw, float step, int tickInterval) {
        super("pingpong", tickInterval);
        this.minYaw = minYaw;
        this.maxYaw = maxYaw;
        this.step = step;
    }

    @Override
    public void tick(Rotatable target) {
        float yaw = target.getCurrentYaw();

        if (forward) {
            yaw += step;
            if (yaw >= maxYaw) {
                yaw = maxYaw;
                forward = false;
            }
        } else {
            yaw -= step;
            if (yaw <= minYaw) {
                yaw = minYaw;
                forward = true;
            }
        }

        target.setCurrentYaw(yaw);
        target.rotate();
    }
}
