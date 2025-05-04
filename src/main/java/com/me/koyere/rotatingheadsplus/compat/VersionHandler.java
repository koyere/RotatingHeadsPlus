package com.me.koyere.rotatingheadsplus.compat;

public class VersionHandler {

    private static VersionAdapter adapter;

    /**
     * Inicializa el adaptador de versión basado en la versión actual del servidor.
     *
     * @param bukkitVersion Versión en formato "v1_14_R1", "v1_20_R1", etc.
     */
    public static void init(String bukkitVersion) {
        if (bukkitVersion.startsWith("v1_14")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_14_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_15")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_15_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_16")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_16_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_17")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_17_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_18")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_18_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_19")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_19_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_20")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_20_R1.VersionAdapterImpl();
        } else if (bukkitVersion.startsWith("v1_21")) {
            adapter = new com.me.koyere.rotatingheadsplus.compat.versions.v1_21_R1.VersionAdapterImpl();
        } else {
            throw new UnsupportedOperationException("Unsupported server version: " + bukkitVersion);
        }
    }

    /**
     * Devuelve el adaptador cargado.
     *
     * @return Instancia de VersionAdapter correspondiente a la versión del servidor.
     */
    public static VersionAdapter getAdapter() {
        return adapter;
    }
}



