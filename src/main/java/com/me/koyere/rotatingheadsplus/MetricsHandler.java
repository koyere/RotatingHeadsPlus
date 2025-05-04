package com.me.koyere.rotatingheadsplus;

import com.me.koyere.rotatingheadsplus.metrics.Metrics;
import com.me.koyere.rotatingheadsplus.metrics.charts.SimplePie;
import org.bukkit.Bukkit;

/**
 * Initializes bStats metrics for RotatingHeadsPlus.
 */
public class MetricsHandler {

    private final RotatingHeadsPlus plugin;

    public MetricsHandler(RotatingHeadsPlus plugin) {
        this.plugin = plugin;
    }

    public void start() {
        int pluginId = 25728; // Your official bStats plugin ID

        Metrics metrics = new Metrics(plugin, pluginId);

        // Pie chart for server version (e.g., 1.21.5)
        metrics.addCustomChart(new SimplePie("server_version", () ->
                Bukkit.getBukkitVersion().split("-")[0]
        ));

        // Pie chart for selected language from config
        metrics.addCustomChart(new SimplePie("language", () ->
                plugin.getConfigManager().getLanguageCode()
        ));
    }
}
