package com.example.shopstore.common;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class StatusCommon {
    private Map<Integer, String> status;
    private final String isActiveStr = "Hoạt động";
    private final String inActiveStr = "Dừng hoạt động";
    private final int isActive = 1;
    private final int inActive = 0;

    public StatusCommon() {
        Map<Integer, String> map = new HashMap<>();
        map.put(inActive, inActiveStr);
        map.put(isActive, isActiveStr);
        this.status = map;
    }

    public Map<Integer, String> getStatus() {
        return status;
    }
}
