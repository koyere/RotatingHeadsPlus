package com.me.koyere.rotatingheadsplus.config;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;

import java.io.File;

/**
 * Copies example animation files from resources/ to the plugin's folders on first run.
 * Ensures users have useful YAML templates for learning and testing.
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

        // === Examples to copy into /examples/ folder ===
        copy("examples/head_pingpong.yml");
        copy("examples/head_circular.yml");
        copy("examples/armorstand_rotation.yml");
        copy("examples/entity_spin.yml");

        // NEW: Advanced examples for click actions and conditions
        copy("examples/click_actions_example.yml");
        copy("examples/head_timed_conditional.yml");

        // === Simple animation example to /animations/ folder ===
        copy("animations/example.yml");
    }

    /**
     * Copies a resource from the plugin's JAR into the plugin's data folder,
     * only if it doesn't already exist in the destination.
     *
     * @param path Path relative to the resources folder (e.g., examples/file.yml or animations/example.yml)
     */
    private void copy(String path) {
        File dest = new File(plugin.getDataFolder(), path);
        if (!dest.exists()) {
            plugin.saveResource(path, false);
        }
    }
}
