package com.example.client.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpHeaders {
    private Map<String, String> headers = new HashMap<>();

    public void set(String key, String value) {
        headers.put(key, value);
    }

    public String get(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Set<String> keySet() {
        return headers.keySet();
    }
}
