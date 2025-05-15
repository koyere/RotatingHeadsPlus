package com.me.koyere.rotatingheadsplus.actions;

import org.bukkit.entity.Player;

/**
 * Representa una acción individual a ejecutar al hacer clic,
 * junto con su tipo (COMMAND, MESSAGE, TITLE).
 */
public class ActionEntry {

    private final ClickAction.ActionType type;
    private final String value;

    public ActionEntry(ClickAction.ActionType type, String value) {
        this.type = type;
        this.value = value;
    }

    public ClickAction.ActionType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    /**
     * Ejecuta esta acción en el jugador dado.
     *
     * @param player Jugador que activa la acción.
     */
    public void execute(Player player) {
        switch (type) {
            case COMMAND:
                player.performCommand(value.replace("%player%", player.getName()));
                break;
            case MESSAGE:
                player.sendMessage(value.replace("%player%", player.getName()));
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
