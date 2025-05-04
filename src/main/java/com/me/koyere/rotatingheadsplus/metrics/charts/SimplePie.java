package com.me.koyere.rotatingheadsplus.metrics.charts;

import com.me.koyere.rotatingheadsplus.metrics.json.JsonObjectBuilder;

import java.util.concurrent.Callable;

public class SimplePie extends CustomChart {

    private final Callable<String> callable;

    public SimplePie(String chartId, Callable<String> callable) {
        super(chartId);
        this.callable = callable;
    }

    @Override
    public JsonObjectBuilder.JsonObject getChartData() throws Exception {
        String value = callable.call();
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new JsonObjectBuilder().appendField("value", value).build();
    }
}
