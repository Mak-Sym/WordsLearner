package com.maksym.android.words.learner.utils;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Maps {
    public static <K, V> Map<K, V> map(Entry<K, V>... entries) {
        Map<K, V> map = new HashMap<>();
        for(Entry<K, V> entry : entries) {
            map.put(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> Entry<K, V> entry(K key, V value) {
        return new SimpleImmutableEntry(key, value);
    }
}
