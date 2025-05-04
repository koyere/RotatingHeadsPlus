package com.me.koyere.rotatingheadsplus;

import com.me.koyere.rotatingheadsplus.animation.AnimationLoader;
import com.me.koyere.rotatingheadsplus.animation.AnimationScheduler;
import com.me.koyere.rotatingheadsplus.commands.HeadCommand;
import com.me.koyere.rotatingheadsplus.compat.CompatProvider;
import com.me.koyere.rotatingheadsplus.config.ConfigManager;
import com.me.koyere.rotatingheadsplus.config.DataManager;
import com.me.koyere.rotatingheadsplus.config.ExampleLoader;
import com.me.koyere.rotatingheadsplus.head.HeadManager;
import com.me.koyere.rotatingheadsplus.lang.LangManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for RotatingHeadsPlus.
 * Registers commands, managers, and starts animation scheduler.
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

        // Crear config.yml si no existe
        saveDefaultConfig();
        new ExampleLoader().copyExamples();

        // Inicializar compatibilidad con versiones
        CompatProvider.initialize();

        // Inicializar gestores
        this.configManager = new ConfigManager(this);
        this.langManager = new LangManager();
        this.langManager.load(configManager.getLanguageCode());

        this.headManager = new HeadManager();
        this.dataManager = new DataManager();
        this.animationScheduler = new AnimationScheduler();

        // Registrar comandos
        getCommand("rhead").setExecutor(new HeadCommand());

        // Cargar datos desde carpeta /data
        dataManager.loadHeads();

        // Iniciar animaciones programadas
        animationScheduler.start();

        // Cargar animaciones desde /animations/*.yml
        new AnimationLoader(this).loadAllAnimations();

        // Iniciar bStats
        new MetricsHandler(this).start();

        getLogger().info("RotatingHeadsPlus has been enabled.");
    }

    @Override
    public void onDisable() {
        if (animationScheduler != null) {
            animationScheduler.stop();
        }
        getLogger().info("RotatingHeadsPlus has been disabled.");
    }

    // === Getters ===

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

