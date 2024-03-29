package com.sb.sunsecho.beans;

import android.net.Uri;

import java.util.Locale;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

public abstract class ApiQuery implements QueryArguments {

    public static String defaultLanguage = Locale.getDefault().getLanguage();

    /**
     * Keywords or a phrase to search for in the article title and body.
     */
    @Nullable
    protected String keywords;

    /**
     * A list of identifiers for the news sources or blogs the headlines must come from.
     * <p>
     * WARNING: Cannot be mixed with `country` or `category` params.
     */
    @Nullable
    protected String[] sources;
    /**
     * The 2-letter ISO-639-1 code of the language you want to get headlines for.
     * If null, articles of any languages could be returned.
     */
    @Nullable
    protected String language = defaultLanguage;
    /**
     * The number of results to return per page (request). 20 is the default, 100 is the maximum.
     */
    @Nullable
    @IntRange(from = 1, to = 100)
    protected Integer pageSize;
    /**
     * Use this to page through the results if the total results found is greater than the page size.
     */
    @Nullable
    protected Integer page;

    public ApiQuery(@Nullable String keywords, @Nullable String[] sources, @Nullable String language, @Nullable Integer pageSize, @Nullable Integer page) {
        this.keywords = keywords;
        this.sources = sources;
        this.language = language;
        this.pageSize = pageSize;
        this.page = page;
    }

    @Nullable
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(@Nullable String keywords) {
        this.keywords = keywords;
    }

    @Nullable
    public String[] getSources() {
        return sources;
    }

    public void setSources(@Nullable String[] sources) {
        this.sources = sources;
    }

    @Nullable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(@Nullable String language) {
        this.language = language;
    }

    @Nullable
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(@Nullable Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Nullable
    public Integer getPage() {
        return page;
    }

    public void setPage(@Nullable Integer page) {
        this.page = page;
    }

    @Override
    public Uri.Builder toUrlArguments(Uri.Builder builder) {
        if (keywords != null) builder.appendQueryParameter("q", keywords);
        if (sources != null && sources.length != 0) builder.appendQueryParameter("sources", String.join(",", sources));
        if (language != null) builder.appendQueryParameter("language", language);
        if (pageSize != null) builder.appendQueryParameter("page_size ", pageSize.toString());
        if (page != null) builder.appendQueryParameter("page", page.toString());
        completeArguments(builder);
        return builder;
    }

    public boolean isAllArgumentsNull() {
        return keywords == null
                && (sources == null || sources.length == 0)
                && language == null
                && pageSize == null
                && page == null
                && isChildArgumentsNull();
    }

    protected abstract boolean isChildArgumentsNull();

    /**
     * Complete the arguments list of the request.
     * @param builder
     */
    protected abstract void completeArguments(Uri.Builder builder);

    public static abstract class Builder {
        protected String keywords;
        protected String[] sources;
        protected String language = defaultLanguage;
        protected Integer pageSize;
        protected Integer page;

        public Builder() {
        }

        public Builder withKeywords(String keywords) {
            this.keywords = keywords;
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

        public abstract ApiQuery build();
    }
}
