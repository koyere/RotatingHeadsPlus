package com.me.koyere.rotatingheadsplus.config;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;

import java.io.File;

/**
 * Copies example animation files from resources/ to the plugin's folders on first run.
 */
public class ExampleLoader {

    private final RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

    /**
     * Copies all example YAML files to their respective folders if they don't exist.
     */
    public void copyExamples() {
        File examplesFolder = new File(plugin.getDataFolder(), "examples");
        File animationsFolder = new File(plugin.getDataFolder(), "animations");

        if (!examplesFolder.exists()) {
            examplesFolder.mkdirs();
        }

        if (!animationsFolder.exists()) {
            animationsFolder.mkdirs();
        }

        // Archivos para carpeta examples/
        copy("examples/head_pingpong.yml");
        copy("examples/head_circular.yml");
        copy("examples/armorstand_rotation.yml");
        copy("examples/entity_spin.yml");

        // Archivo de ejemplo para carpeta animations/
        copy("animations/example.yml");
    }

    /**
     * Copies a resource from the JAR into the plugin's data folder, if it does not already exist.
     * @param path Path inside the resources folder (e.g. examples/file.yml or animations/example.yml)
     */
    private void copy(String path) {
        File dest = new File(plugin.getDataFolder(), path);
        if (!dest.exists()) {
            plugin.saveResource(path, false);
        }
    }
}

