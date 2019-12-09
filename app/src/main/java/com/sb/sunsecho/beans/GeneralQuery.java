package com.sb.sunsecho.beans;

import android.net.Uri;

import java.time.Instant;

import androidx.annotation.Nullable;

public class GeneralQuery extends ApiQuery {
    /**
     * Keywords or a phrase to search for in the article title and body.
     */
    @Nullable
    protected String inTitle;

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

    public GeneralQuery(@Nullable String keywords, @Nullable String[] sources, @Nullable String language, @Nullable Integer pageSize, @Nullable Integer page, @Nullable String inTitle, @Nullable String[] domains, @Nullable String[] excludeDomains, @Nullable Instant from, @Nullable Instant to, @Nullable SortBy sortBy) {
        super(keywords, sources, language, pageSize, page);
        this.inTitle = inTitle;
        this.domains = domains;
        this.excludeDomains = excludeDomains;
        this.from = from;
        this.to = to;
        this.sortBy = sortBy;
    }

    @Nullable
    public String getInTitle() {
        return inTitle;
    }

    public void setInTitle(@Nullable String inTitle) {
        this.inTitle = inTitle;
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
    protected boolean isChildArgumentsNull() {
        return inTitle == null
                && (domains == null || domains.length == 0)
                && (excludeDomains == null || excludeDomains.length == 0)
                && from == null
                && to == null
                && sortBy == null;
    }

    @Override
    protected void completeArguments(Uri.Builder builder) {
        if (inTitle != null) builder.appendQueryParameter("qInTitle", inTitle);
        if (domains != null && domains.length != 0) builder.appendQueryParameter("domains", String.join(",", domains));
        if (excludeDomains != null && excludeDomains.length != 0) builder.appendQueryParameter("excludeDomains", String.join(",", excludeDomains));
        if (from != null) builder.appendQueryParameter("from", from.toString());
        if (to != null) builder.appendQueryParameter("to", to.toString());
        if (sortBy != null) builder.appendQueryParameter("sortBy", sortBy.name());
    }


    public static final class Builder extends ApiQuery.Builder {
        protected String inTitle;
        protected String[] domains;
        protected String[] excludeDomains;
        protected Instant from;
        protected Instant to;
        protected SortBy sortBy;

        public Builder() {
        }

        public static Builder aGeneralQuery() {
            return new Builder();
        }

        public Builder withInTitle(String inTitle) {
            this.inTitle = inTitle;
            return this;
        }

        public Builder withDomains(String[] domains) {
            this.domains = domains;
            return this;
        }

        public Builder withExcludeDomains(String[] excludeDomains) {
            this.excludeDomains = excludeDomains;
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

        public Builder withSortBy(SortBy sortBy) {
            this.sortBy = sortBy;
            return this;
        }

        public GeneralQuery build() {
            return new GeneralQuery(keywords, sources, language, pageSize, page, inTitle, domains, excludeDomains, from, to, sortBy);
        }
    }
}
