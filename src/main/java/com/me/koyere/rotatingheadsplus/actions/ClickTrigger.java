package com.me.koyere.rotatingheadsplus.actions;

/**
 * Enum para determinar cuándo deben ejecutarse las acciones al hacer clic.
 */
public enum ClickTrigger {
    LEFT,
    RIGHT,
    BOTH;

    /**
     * Convierte un string a ClickTrigger, predeterminando a BOTH si es inválido.
     */
    public static ClickTrigger fromString(String input) {
        try {
            return ClickTrigger.valueOf(input.toUpperCase());
        } catch (Exception e) {
            return BOTH;
        }
    }
}
