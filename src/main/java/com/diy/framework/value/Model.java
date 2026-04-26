package com.diy.framework.value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record Model(
        Map<String, Object> attributes
) {
    public Model {
        attributes = Map.copyOf(attributes);
    }

    public static Model empty() {
        return new Model(Collections.emptyMap());
    }

    public Model with(String key, Object value) {
        final Map<String, Object> next = new HashMap<>(attributes);
        next.put(key, value);
        return new Model(next);
    }
}
