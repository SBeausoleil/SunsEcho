package com.sb.sunsecho.beans;

import android.net.Uri;

import java.time.Instant;

import androidx.annotation.Nullable;

public class GeneralQuery extends ApiQuery {
    /**
     * A list of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to restrict the search to.
     */
    @Nullable
    protected String[] domains;
    /**
     * A list of domains (eg bbc.co.uk, techcrunch.com, engadget.com) to remove from the results.
     */
    @Nullable
    protected String[] excludeDomains;
    /**
     * A date and optional time for the oldest article allowed.
     */
    @Nullable
    protected Instant from;
    /**
     * A date and optional time for the newest article allowed.
     */
    @Nullable
    protected Instant to;
    /**
     * The order to sort the articles in.
     */
    @Nullable
    protected SortBy sortBy;

    public GeneralQuery(@Nullable String keywords, @Nullable String inTitle, @Nullable String[] sources, @Nullable String language, @Nullable Integer pageSize, @Nullable Integer page, @Nullable String[] domains, @Nullable String[] excludeDomains, @Nullable Instant from, @Nullable Instant to, @Nullable SortBy sortBy) {
        super(keywords, inTitle, sources, language, pageSize, page);
        this.domains = domains;
        this.excludeDomains = excludeDomains;
        this.from = from;
        this.to = to;
        this.sortBy = sortBy;
    }

    @Nullable
    public String[] getDomains() {
        return domains;
    }

    public void setDomains(@Nullable String[] domains) {
        this.domains = domains;
    }

    @Nullable
    public String[] getExcludeDomains() {
        return excludeDomains;
    }

    public void setExcludeDomains(@Nullable String[] excludeDomains) {
        this.excludeDomains = excludeDomains;
    }

    @Nullable
    public Instant getFrom() {
        return from;
    }

    public void setFrom(@Nullable Instant from) {
        this.from = from;
    }

    @Nullable
    public Instant getTo() {
        return to;
    }

    public void setTo(@Nullable Instant to) {
        this.to = to;
    }

    @Nullable
    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(@Nullable SortBy sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    protected void completeArguments(Uri.Builder builder) {
        if (domains != null && domains.length != 0) builder.appendQueryParameter("domains", String.join(",", domains));
        if (excludeDomains != null && excludeDomains.length != 0) builder.appendQueryParameter("excludeDomains", String.join(",", excludeDomains));
        if (from != null) builder.appendQueryParameter("from", from.toString());
        if (to != null) builder.appendQueryParameter("to", to.toString());
        if (sortBy != null) builder.appendQueryParameter("sortBy", sortBy.name());
    }

    public static final class Builder {
        private String[] domains;
        private String keywords;
        private String[] excludeDomains;
        private String inTitle;
        private Instant from;
        private Instant to;
        private String[] sources;
        private SortBy sortBy;
        private String language = ApiQuery.defaultLanguage;
        private Integer pageSize;
        private Integer page;

        public Builder() {
        }

        public Builder withDomains(String[] domains) {
            this.domains = domains;
            return this;
        }

        public Builder withKeywords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public Builder withExcludeDomains(String[] excludeDomains) {
            this.excludeDomains = excludeDomains;
            return this;
        }

        public Builder withInTitle(String inTitle) {
            this.inTitle = inTitle;
            return this;
        }

        public Builder withFrom(Instant from) {
            this.from = from;
            return this;
        }

        public Builder withTo(Instant to) {
            this.to = to;
            return this;
        }

        public Builder withSources(String[] sources) {
            this.sources = sources;
            return this;
        }

        public Builder withSortBy(SortBy sortBy) {
            this.sortBy = sortBy;
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

        public GeneralQuery build() {
            return new GeneralQuery(keywords, inTitle, sources, language, pageSize, page, domains, excludeDomains, from, to, sortBy);
        }
    }
}
