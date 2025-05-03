package com.me.koyere.rotatingheadsplus.config;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;

/**
 * Encapsulates access to values defined in config.yml.
 * Requires reference to the plugin to access the config.
 */
public class ConfigManager {

    // Reference to the main plugin
    private final RotatingHeadsPlus plugin;

    /**
     * Constructor that requires a reference to the main plugin class.
     * This resolves the error: "Default constructor is invoked with arguments".
     */
    public ConfigManager(RotatingHeadsPlus plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the language code selected in config.yml (e.g., "en_US").
     */
    public String getLanguageCode() {
        return plugin.getConfig().getString("language", "en_US");
    }

    /**
     * Default yaw rotation speed per tick used in /rhead create.
     */
    public float getDefaultSpeed() {
        return (float) plugin.getConfig().getDouble("defaultSpeed", 5.0);
    }

    /**
     * Default interval (in ticks) between animation updates.
     */
    public int getDefaultInterval() {
        return plugin.getConfig().getInt("defaultInterval", 2);
    }

    /**
     * Max distance (in blocks) to search for nearby rotatable objects.
     * Used in /rhead remove.
     */
    public double getMaxRemovalDistance() {
        return plugin.getConfig().getDouble("maxRemovalDistance", 3.0);
    }

    /**
     * Whether debug messages should be printed in the console.
     */
    public boolean isDebugEnabled() {
        return plugin.getConfig().getBoolean("debug", false);
    }
}
