package com.sb.sunsecho.utils;

import com.sb.sunsecho.beans.Source;

import java.util.HashMap;

public class Sources {
    private HashMap<String, Source> sourcesById;

    public Sources(Source[] sources) {
        sourcesById = new HashMap<>(sources.length);
        for (Source source : sources)
            sourcesById.put(source.getId(), source);
    }

    public Source getById(String id) {
        return sourcesById.get(id);
    }
}
