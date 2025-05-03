package com.me.koyere.rotatingheadsplus.animation;

/**
 * Rotates the entity continuously in a single direction.
 */
public class CircularRotation extends HeadAnimation {

    private float yaw;
    private final float speed;
    private final boolean clockwise;

    /**
     * @param startYaw Initial yaw angle (e.g. 0f)
     * @param speed How much to rotate per tick (positive = faster)
     * @param clockwise Direction of rotation
     * @param tickInterval How often to update (1 = every tick)
     */
    public CircularRotation(float startYaw, float speed, boolean clockwise, int tickInterval) {
        super("circular", tickInterval);
        this.yaw = startYaw;
        this.speed = speed;
        this.clockwise = clockwise;
    }

    @Override
    public void tick(Rotatable target) {
        yaw += clockwise ? speed : -speed;
        if (yaw >= 360) yaw -= 360;
        if (yaw <= -360) yaw += 360;

        target.setCurrentYaw(yaw);
        target.rotate();
    }
}
