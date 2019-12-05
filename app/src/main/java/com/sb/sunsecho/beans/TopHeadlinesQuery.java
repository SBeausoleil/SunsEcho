package com.sb.sunsecho.beans;

import android.net.Uri;

import androidx.annotation.Nullable;

public class TopHeadlinesQuery extends ApiQuery {

    /**
     * Country you want headlines for.
     * <p>
     * WARNING: Cannot be mixed with `sources` tag.
     */
    @Nullable
    protected String country;
    /**
     * The category you want to get headlines for.
     * <p>
     * WARNING: Cannot be mixed with `sources` tag.
     */
    @Nullable
    protected Category category;

    public TopHeadlinesQuery(@Nullable String keywords, @Nullable String inTitle, @Nullable String[] sources, @Nullable String language, @Nullable Integer pageSize, @Nullable Integer page, @Nullable String country, @Nullable Category category) {
        super(keywords, inTitle, sources, language, pageSize, page);
        this.country = country;
        this.category = category;
    }

    @Override
    protected void completeArguments(Uri.Builder builder) {
        if (country != null) builder.appendQueryParameter("country", country);
        if (category != null) builder.appendQueryParameter("category", category.name());
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public void setCountry(@Nullable String country) {
        this.country = country;
    }

    @Nullable
    public Category getCategory() {
        return category;
    }

    public void setCategory(@Nullable Category category) {
        this.category = category;
    }

    public static final class Builder extends ApiQuery.Builder {
        private String country;
        private Category category;

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        @Override
        public TopHeadlinesQuery build() {
            return new TopHeadlinesQuery(keywords, inTitle, sources, language, pageSize, page, country, category);
        }
    }
}
