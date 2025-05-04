package com.me.koyere.rotatingheadsplus.metrics.json;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObjectBuilder {

    private final Map<String, Object> values = new LinkedHashMap<>();

    public JsonObjectBuilder appendField(String key, Object value) {
        values.put(key, value);
        return this;
    }

    public JsonObject build() {
        return new JsonObject(values);
    }

    public static class JsonObject {
        private final Map<String, Object> values;

        public JsonObject(Map<String, Object> values) {
            this.values = values;
        }

        public Map<String, Object> getValues() {
            return values;
        }
    }
}
