package com.me.koyere.rotatingheadsplus.animation;

/**
 * Base class for all animations applied to Rotatable entities.
 */
public abstract class HeadAnimation {

    private final String name;
    private final int tickInterval;

    /**
     * @param name Internal name of the animation (e.g., "circular", "pingpong").
     * @param tickInterval Interval between animation ticks (in server ticks).
     */
    public HeadAnimation(String name, int tickInterval) {
        this.name = name;
        this.tickInterval = tickInterval;
    }

    /**
     * Returns the internal name of the animation.
     */
    public String getName() {
        return name;
    }

    /**
     * How many ticks between each execution of the tick() method.
     */
    public int getTickInterval() {
        return tickInterval;
    }

    /**
     * Called periodically by the animation scheduler.
     */
    public abstract void tick(Rotatable target);
}

