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


    public static final class Builder {
        private String country;
        private String keywords;
        private Category category;
        private String inTitle;
        private String[] sources;
        private String language = ApiQuery.defaultLanguage;
        private Integer pageSize;
        private Integer page;

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withKeywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder withCategory(Category category) {
            this.category = category;
            return this;
        }

        public Builder withInTitle(String inTitle) {
            this.inTitle = inTitle;
            return this;
        }

        public Builder withSources(String[] sources) {
            this.sources = sources;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder withPageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public TopHeadlinesQuery build() {
            return new TopHeadlinesQuery(keywords, inTitle, sources, language, pageSize, page, country, category);
        }
    }
}
