package com.sb.sunsecho.beans;

import android.net.Uri;

import androidx.annotation.Nullable;

public class SourceQuery implements QueryArguments {

    @Nullable
    protected Category category;
    @Nullable
    private String language;
    @Nullable
    private String country;

    public SourceQuery() {}

    public SourceQuery(Category category, String language, String country) {
        this.category = category;
        this.language = language;
        this.country = country;
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
    public Uri.Builder toUrlArguments(Uri.Builder builder) {
        if (category != null) builder.appendQueryParameter("category", category.name());
        if (language != null) builder.appendQueryParameter("language", language);
        if (country != null) builder.appendQueryParameter("country", country);
        return builder;
    }

    public static final class Builder {
        protected Category category;
        private String language;
        private String country;

        private Builder() {
        }

        public static Builder aSourceQuery() {
            return new Builder();
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public SourceQuery build() {
            return new SourceQuery(category, language, country);
        }
    }
}
