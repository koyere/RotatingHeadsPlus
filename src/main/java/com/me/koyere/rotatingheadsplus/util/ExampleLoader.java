package com.me.koyere.rotatingheadsplus.util;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class ExampleLoader {

    private final RotatingHeadsPlus plugin;

    public ExampleLoader(RotatingHeadsPlus plugin) {
        this.plugin = plugin;
    }

    /**
     * Copia los archivos de ejemplo desde resources/lang/examples/ al directorio plugins/RotatingHeadsPlus/examples/
     */
    public void createExamples() {
        File examplesFolder = new File(plugin.getDataFolder(), "examples");
        if (!examplesFolder.exists()) {
            examplesFolder.mkdirs();
        }

        copyExample("head_basic.yml");
        copyExample("head_pingpong.yml");
    }

    private void copyExample(String filename) {
        File target = new File(plugin.getDataFolder(), "examples/" + filename);
        if (target.exists()) return;

        try (InputStream in = plugin.getResource("examples/" + filename);
             OutputStream out = Files.newOutputStream(target.toPath())) {
            if (in != null) {
                in.transferTo(out);
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to copy example file: " + filename);
            e.printStackTrace();
        }
    }
}
