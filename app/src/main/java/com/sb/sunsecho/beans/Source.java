package com.sb.sunsecho.beans;

import java.net.URL;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Source {
    @Nullable
    private String id;
    @NonNull
    private String name;

    private String description;
    private URL url;
    private Category category;
    private String language;
    private String country;

    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source(@Nullable String id, @NonNull String name, String description, URL url, Category category, String language, String country) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return (id != null && id.equals(source.id))
                || (id == null || source.id == null) && name.equalsIgnoreCase(source.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


    @Override
    public String toString() {
        return "Source{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url=" + url +
                ", category=" + category +
                ", language=" + language +
                ", country=" + country +
                '}';
    }
}
