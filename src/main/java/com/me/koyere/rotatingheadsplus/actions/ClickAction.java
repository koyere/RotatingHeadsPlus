package com.me.koyere.rotatingheadsplus.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Representa una o más acciones que se ejecutan al hacer clic sobre un objeto animado.
 * Soporta múltiples acciones (comando, mensaje, título) y tipo de clic (izquierdo, derecho o ambos).
 */
public class ClickAction {

    public enum ActionType {
        COMMAND, MESSAGE, TITLE
    }

    private final ClickTrigger trigger;
    private final List<ActionEntry> actions;

    public ClickAction(ClickTrigger trigger, List<ActionEntry> actions) {
        this.trigger = trigger;
        this.actions = actions;
    }

    /**
     * Ejecuta todas las acciones asociadas.
     */
    public void execute(Player player) {
        for (ActionEntry entry : actions) {
            String value = entry.value.replace("%player%", player.getName());
            switch (entry.type) {
                case COMMAND:
                    player.performCommand(value);
                    break;
                case MESSAGE:
                    player.sendMessage(value);
                    break;
                case TITLE:
                    String[] parts = value.split("::", 2);
                    String title = parts.length > 0 ? parts[0] : "";
                    String subtitle = parts.length > 1 ? parts[1] : "";
                    player.sendTitle(title, subtitle, 10, 40, 10);
                    break;
            }
        }
    }

    public ClickTrigger getTrigger() {
        return trigger;
    }

    public List<ActionEntry> getActions() {
        return actions;
    }

    /**
     * Representa una acción individual.
     */
    public static class ActionEntry {
        public final ActionType type;
        public final String value;

        public ActionEntry(ActionType type, String value) {
            this.type = type;
            this.value = value;
        }
    }
}
