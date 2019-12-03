package com.sb.sunsecho.utils;

import com.sb.sunsecho.beans.Source;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Sources {
    private HashMap<String, Source> sourcesById;

    public Sources(Source[] sources) {
        sourcesById = new HashMap<>(sources.length);
        for (Source source : sources)
            sourcesById.put(source.getId(), source);
    }

    public Sources(Collection<Source> sources) {
        sourcesById = new HashMap<>(sources.size());
        for (Source source : sources)
            sourcesById.put(source.getId(), source);
    }

    public Source getById(String id) {
        return sourcesById.get(id);
    }

    public void forEach(@NonNull BiConsumer<? super String, ? super Source> action) {
        sourcesById.forEach(action);
    }

    public int size() {
        return sourcesById.size();
    }

    public Source get(@Nullable Object key) {
        return sourcesById.get(key);
    }

    public Set<String> keySet() {
        return sourcesById.keySet();
    }

    public Collection<Source> values() {
        return sourcesById.values();
    }

    public Set<Map.Entry<String, Source>> entrySet() {
        return sourcesById.entrySet();
    }
}
