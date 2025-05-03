package com.me.koyere.rotatingheadsplus.util;

import java.util.concurrent.ThreadLocalRandom;

public class PacketUtils {

    /**
     * Genera un ID de entidad aleatorio entre 100_000 y 999_999.
     * Útil si se requiere simular entidades temporales o identificadores únicos.
     */
    public static int generateRandomEntityId() {
        return ThreadLocalRandom.current().nextInt(100_000, 1_000_000);
    }
}
