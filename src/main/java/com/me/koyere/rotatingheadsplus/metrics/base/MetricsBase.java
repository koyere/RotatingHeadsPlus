package com.me.koyere.rotatingheadsplus.metrics.base;

import com.me.koyere.rotatingheadsplus.metrics.charts.CustomChart;
import com.me.koyere.rotatingheadsplus.metrics.json.JsonObjectBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MetricsBase {

    private final List<CustomChart> charts = new ArrayList<>();
    private final String platform;
    private final String serverUUID;
    private final int serviceId;
    private final boolean enabled;
    private final Consumer<JsonObjectBuilder> platformData;
    private final Consumer<JsonObjectBuilder> serviceData;
    private final Consumer<Runnable> submitTask;
    private final Callable<Boolean> pluginEnabled;
    private final BiConsumer<String, Throwable> errorLogger;
    private final Consumer<String> infoLogger;
    private final boolean logErrors, logSentData, logResponseStatusText;

    public MetricsBase(
            String platform,
            String serverUUID,
            int serviceId,
            boolean enabled,
            Consumer<JsonObjectBuilder> platformData,
            Consumer<JsonObjectBuilder> serviceData,
            Consumer<Runnable> submitTask,
            Callable<Boolean> pluginEnabled,
            BiConsumer<String, Throwable> errorLogger,
            Consumer<String> infoLogger,
            boolean logErrors,
            boolean logSentData,
            boolean logResponseStatusText
    ) {
        this.platform = platform;
        this.serverUUID = serverUUID;
        this.serviceId = serviceId;
        this.enabled = enabled;
        this.platformData = platformData;
        this.serviceData = serviceData;
        this.submitTask = submitTask;
        this.pluginEnabled = pluginEnabled;
        this.errorLogger = errorLogger;
        this.infoLogger = infoLogger;
        this.logErrors = logErrors;
        this.logSentData = logSentData;
        this.logResponseStatusText = logResponseStatusText;
    }

    public void addCustomChart(CustomChart chart) {
        if (chart != null) charts.add(chart);
    }

    public void shutdown() {
        charts.clear();
    }

    public interface Callable<V> {
        V call() throws Exception;
    }
}
