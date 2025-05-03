package com.me.koyere.rotatingheadsplus.animation;

import com.me.koyere.rotatingheadsplus.head.RotatingHead;
import com.me.koyere.rotatingheadsplus.util.PacketUtils;
import org.bukkit.Location;

/**
 * Clase base para definir animaciones personalizadas aplicables a RotatingHead.
 * Subclases deben implementar cómo se calcula la siguiente ubicación.
 */
public abstract class HeadAnimationExtender {

    private final String name;
    private final int id;

    public HeadAnimationExtender(String name) {
        this.name = name;
        this.id = PacketUtils.generateRandomEntityId(); // No afecta a la API, útil si se amplía
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    /**
     * Método que define la siguiente posición hacia la que debe rotar la cabeza.
     */
    public abstract Location pingLocation(RotatingHead rotatingHead);

    /**
     * Aplica la rotación resultante de pingLocation al objeto.
     */
    public void rotate(RotatingHead rotatingHead) {
        rotatingHead.setLastLocation(pingLocation(rotatingHead));
    }
}
