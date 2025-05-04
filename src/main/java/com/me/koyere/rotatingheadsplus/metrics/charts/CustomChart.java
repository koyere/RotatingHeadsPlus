package com.me.koyere.rotatingheadsplus.metrics.charts;

import com.me.koyere.rotatingheadsplus.metrics.json.JsonObjectBuilder;

public abstract class CustomChart {

    private final String chartId;

    public CustomChart(String chartId) {
        this.chartId = chartId;
    }

    public String getChartId() {
        return chartId;
    }

    public abstract JsonObjectBuilder.JsonObject getChartData() throws Exception;
}
