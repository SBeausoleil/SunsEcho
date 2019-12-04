package com.sb.sunsecho.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Maps {

    public static <K, V> LinkedHashMap<K, V> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        return map.entrySet().stream().sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }
}
