package com.me.koyere.rotatingheadsplus;

import com.me.koyere.rotatingheadsplus.animation.AnimationScheduler;
import com.me.koyere.rotatingheadsplus.commands.HeadCommand;
import com.me.koyere.rotatingheadsplus.config.ConfigManager;
import com.me.koyere.rotatingheadsplus.config.DataManager;
import com.me.koyere.rotatingheadsplus.config.ExampleLoader;
import com.me.koyere.rotatingheadsplus.head.HeadManager;
import com.me.koyere.rotatingheadsplus.lang.LangManager;
import com.me.koyere.rotatingheadsplus.listeners.HeadPlaceListener;
import com.me.koyere.rotatingheadsplus.placeholder.RotatingHeadsPlaceholder;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the RotatingHeadsPlus plugin.
 * Initializes all managers and systems during plugin startup.
 */
public final class RotatingHeadsPlus extends JavaPlugin {

    private static RotatingHeadsPlus instance;

    private HeadManager headManager;
    private AnimationScheduler animationScheduler;
    private ConfigManager configManager;
    private LangManager langManager;
    private DataManager dataManager;

    @Override
    public void onEnable() {
        instance = this;

        // Generate config.yml if missing
        saveDefaultConfig();

        // Copy example files from /resources/examples/ to /plugins/RotatingHeadsPlus/examples/
        new ExampleLoader().copyExamples();

        // Initialize managers
        this.configManager = new ConfigManager(this);
        this.langManager = new LangManager();
        this.langManager.load(configManager.getLanguageCode());

        this.headManager = new HeadManager();
        this.dataManager = new DataManager();
        this.animationScheduler = new AnimationScheduler();

        // Register main command
        getCommand("rhead").setExecutor(new HeadCommand());

        // Register event listeners
        getServer().getPluginManager().registerEvents(new HeadPlaceListener(), this);

        // Load all YAML animations from /data/
        dataManager.loadHeads();

        // Start scheduled animation task
        animationScheduler.start();

        // Register PlaceholderAPI hook if available
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new RotatingHeadsPlaceholder(this).register();
            getLogger().info("PlaceholderAPI hook registered.");
        }

        getLogger().info("RotatingHeadsPlus has been enabled.");
    }

    @Override
    public void onDisable() {
        // Stop scheduled animation task
        if (animationScheduler != null) {
            animationScheduler.stop();
        }

        // Save all active rotating objects to /data/
        if (dataManager != null) {
            dataManager.saveAll();
        }

        getLogger().info("RotatingHeadsPlus has been disabled.");
    }

    // === Getters for managers ===

    public static RotatingHeadsPlus getInstance() {
        return instance;
    }

    public HeadManager getHeadManager() {
        return headManager;
    }

    public AnimationScheduler getAnimationScheduler() {
        return animationScheduler;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LangManager getLangManager() {
        return langManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
