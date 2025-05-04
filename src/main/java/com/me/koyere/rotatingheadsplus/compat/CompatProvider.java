package com.me.koyere.rotatingheadsplus.compat;

import org.bukkit.Bukkit;

/**
 * Inicializa y proporciona el adaptador de versión correspondiente.
 */
public class CompatProvider {

    private static VersionAdapter adapter;

    /**
     * Inicializa el adaptador de compatibilidad dependiendo de la versión del servidor.
     */
    public static void initialize() {
        String bukkitVersion = Bukkit.getBukkitVersion(); // Ej: "1.21-R0.1-SNAPSHOT"
        String[] base = bukkitVersion.split("-")[0].split("\\."); // Ej: ["1", "21"]

        if (base.length < 2) {
            throw new IllegalStateException("No se pudo detectar la versión de Bukkit correctamente: " + bukkitVersion);
        }

        String major = base[1];
        String versionId = "v1_" + major + "_R1";

        VersionHandler.init(versionId);
        adapter = VersionHandler.getAdapter();

        Bukkit.getLogger().info("[RotatingHeadsPlus] Loaded version adapter: " + versionId);
    }

    /**
     * Obtiene el adaptador de versión activo.
     * @return Adaptador activo.
     */
    public static VersionAdapter getAdapter() {
        return adapter;
    }
}

