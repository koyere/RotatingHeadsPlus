package com.me.koyere.rotatingheadsplus.config;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;

import java.io.File;

/**
 * Copies example animation files from resources/examples/ to the plugin's examples/ folder on first run.
 */
public class ExampleLoader {

    private final RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

    /**
     * Copies all example YAML files to /plugins/RotatingHeadsPlus/examples/ if they don't exist.
     */
    public void copyExamples() {
        File examplesFolder = new File(plugin.getDataFolder(), "examples");

        if (!examplesFolder.exists()) {
            examplesFolder.mkdirs();
        }

        copy("examples/head_pingpong.yml");
        copy("examples/head_circular.yml");
        copy("examples/armorstand_rotation.yml");
        copy("examples/entity_spin.yml");
    }

    /**
     * Copies a resource from the JAR into the plugin's data folder, if it does not already exist.
     * @param path Path inside the resources folder (e.g. examples/file.yml)
     */
    private void copy(String path) {
        File dest = new File(plugin.getDataFolder(), path);
        if (!dest.exists()) {
            plugin.saveResource(path, false);
        }
    }
}
