package com.me.koyere.rotatingheadsplus.animation;

import com.me.koyere.rotatingheadsplus.actions.ActionEntry;
import com.me.koyere.rotatingheadsplus.actions.ClickAction;
import com.me.koyere.rotatingheadsplus.actions.ClickTrigger;
import com.me.koyere.rotatingheadsplus.rotating.Rotatable;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

/**
 * Ejecuta una animación sobre un objeto Rotatable, aplicando secuencias de yaw/pitch,
 * siempre y cuando se cumplan las condiciones opcionales definidas.
 * También soporta acciones al hacer clic si se cumplen los triggers.
 */
public class AnimationExecutor {

    private final Plugin plugin;
    private final Rotatable target;
    private final List<float[]> frames;
    private final long intervalTicks;
    private final boolean loop;
    private final AnimationCondition condition;

    private final Map<ClickTrigger, List<ActionEntry>> clickActions = new EnumMap<>(ClickTrigger.class);

    private int currentFrame = 0;
    private BukkitRunnable task;

    public AnimationExecutor(Plugin plugin, Rotatable target, List<float[]> frames, long intervalTicks, boolean loop) {
        this(plugin, target, frames, intervalTicks, loop, null);
    }

    public AnimationExecutor(Plugin plugin, Rotatable target, List<float[]> frames, long intervalTicks, boolean loop, AnimationCondition condition) {
        this.plugin = plugin;
        this.target = target;
        this.frames = frames;
        this.intervalTicks = intervalTicks;
        this.loop = loop;
        this.condition = condition;
    }

    /**
     * Inicia la animación si no está ya ejecutándose.
     */
    public void start() {
        if (task != null) return;

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if (condition != null && !condition.shouldRun(getLocation())) {
                    return;
                }

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

    private Location getLocation() {
        return target.getLocation();
    }

    /**
     * Agrega una acción al grupo de acciones correspondientes al trigger especificado.
     */
    public void addClickAction(ClickTrigger trigger, ActionEntry action) {
        clickActions.computeIfAbsent(trigger, k -> new ArrayList<>()).add(action);
    }

    /**
     * Ejecuta las acciones si el trigger del clic coincide.
     */
    public void handleClick(Player player, PlayerInteractEvent event, ClickTrigger detectedTrigger) {
        if (!clickActions.containsKey(detectedTrigger)) return;
        List<ActionEntry> actions = clickActions.get(detectedTrigger);
        for (ActionEntry action : actions) {
            action.execute(player);
        }
    }
}
