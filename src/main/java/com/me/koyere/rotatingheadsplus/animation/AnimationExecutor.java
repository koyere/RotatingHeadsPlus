package com.me.koyere.rotatingheadsplus.animation;

import com.me.koyere.rotatingheadsplus.rotating.Rotatable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

/**
 * Ejecuta una animación sobre un objeto Rotatable, aplicando secuencias de yaw/pitch.
 */
public class AnimationExecutor {

    private final Plugin plugin;
    private final Rotatable target;
    private final List<float[]> frames;
    private final long intervalTicks;
    private int currentFrame = 0;
    private boolean loop;
    private BukkitRunnable task;

    /**
     * Crea una animación para un objeto rotatable.
     *
     * @param plugin        Instancia del plugin principal.
     * @param target        Objeto rotatable a animar.
     * @param frames        Lista de frames (cada frame es un array {yaw, pitch}).
     * @param intervalTicks Intervalo entre frames en ticks (1 tick = 50 ms).
     * @param loop          Si debe repetirse al terminar.
     */
    public AnimationExecutor(Plugin plugin, Rotatable target, List<float[]> frames, long intervalTicks, boolean loop) {
        this.plugin = plugin;
        this.target = target;
        this.frames = frames;
        this.intervalTicks = intervalTicks;
        this.loop = loop;
    }

    /**
     * Inicia la animación.
     */
    public void start() {
        if (task != null) return;

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (currentFrame >= frames.size()) {
                    if (loop) {
                        currentFrame = 0;
                    } else {
                        cancel();
                        return;
                    }
                }

                float[] frame = frames.get(currentFrame);
                target.rotate(frame[0], frame[1]);
                currentFrame++;
            }
        };

        task.runTaskTimer(plugin, 0L, intervalTicks);
    }

    /**
     * Detiene la animación si está en ejecución.
     */
    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    /**
     * Reinicia la animación desde el primer frame.
     */
    public void restart() {
        stop();
        currentFrame = 0;
        start();
    }
}
