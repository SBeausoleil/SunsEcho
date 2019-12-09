package com.sb.sunsecho.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Source implements Parcelable {
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

    protected Source(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        try {
            String urlString = in.readString();
            if (urlString != null)
                url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        category = in.readParcelable(Category.class.getClassLoader());
        language = in.readString();
        country = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(url != null ? url.toString() : null);
        dest.writeParcelable(category, 0);
        dest.writeString(language);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
