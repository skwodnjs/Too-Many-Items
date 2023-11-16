package net.jwn.jwn_items.util;

import java.util.HashMap;

public interface Timer {
    HashMap<String, Integer> timer = new HashMap<>();

    default int getTime(String tag) {
        if (timer.get(tag) != null) return timer.get(tag);
        return -1;
    }
}
