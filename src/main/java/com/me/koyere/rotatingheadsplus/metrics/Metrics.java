package com.me.koyere.rotatingheadsplus.metrics;

import com.me.koyere.rotatingheadsplus.metrics.base.MetricsBase;
import com.me.koyere.rotatingheadsplus.metrics.charts.CustomChart;
import com.me.koyere.rotatingheadsplus.metrics.json.JsonObjectBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;

public class Metrics {

    private final Plugin plugin;
    private final MetricsBase metricsBase;

    public Metrics(Plugin plugin, int serviceId) {
        this.plugin = plugin;

        File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
        File configFile = new File(bStatsFolder, "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        if (!config.isSet("serverUuid")) {
            config.addDefault("enabled", true);
            config.addDefault("serverUuid", UUID.randomUUID().toString());
            config.addDefault("logFailedRequests", false);
            config.addDefault("logSentData", false);
            config.addDefault("logResponseStatusText", false);
            config.options().header("bStats config");
            config.options().copyDefaults(true);
            try {
                config.save(configFile);
            } catch (IOException ignored) {}
        }

        boolean enabled = config.getBoolean("enabled", true);
        String serverUUID = config.getString("serverUuid");
        boolean logErrors = config.getBoolean("logFailedRequests", false);
        boolean logSentData = config.getBoolean("logSentData", false);
        boolean logResponseStatusText = config.getBoolean("logResponseStatusText", false);

        metricsBase = new MetricsBase(
                "bukkit",
                serverUUID,
                serviceId,
                enabled,
                this::appendPlatformData,
                this::appendServiceData,
                submit -> Bukkit.getScheduler().runTask(plugin, submit),
                plugin::isEnabled,
                (msg, error) -> plugin.getLogger().log(Level.WARNING, msg, error),
                msg -> plugin.getLogger().info(msg),
                logErrors,
                logSentData,
                logResponseStatusText
        );
    }

    public void shutdown() {
        metricsBase.shutdown();
    }

    public void addCustomChart(CustomChart chart) {
        metricsBase.addCustomChart(chart);
    }

    private void appendPlatformData(JsonObjectBuilder builder) {
        builder.appendField("playerAmount", getPlayerAmount());
        builder.appendField("onlineMode", Bukkit.getOnlineMode() ? 1 : 0);
        builder.appendField("bukkitVersion", Bukkit.getVersion());
        builder.appendField("bukkitName", Bukkit.getName());
        builder.appendField("javaVersion", System.getProperty("java.version"));
        builder.appendField("osName", System.getProperty("os.name"));
        builder.appendField("osArch", System.getProperty("os.arch"));
        builder.appendField("osVersion", System.getProperty("os.version"));
        builder.appendField("coreCount", Runtime.getRuntime().availableProcessors());
    }

    private void appendServiceData(JsonObjectBuilder builder) {
        builder.appendField("pluginVersion", plugin.getDescription().getVersion());
    }

    private int getPlayerAmount() {
        try {
            Method method = Bukkit.class.getMethod("getOnlinePlayers");
            if (method.getReturnType().equals(Collection.class)) {
                return ((Collection<?>) method.invoke(null)).size();
            } else {
                return ((Player[]) method.invoke(null)).length;
            }
        } catch (Exception e) {
            return Bukkit.getOnlinePlayers().size();
        }
    }
}
