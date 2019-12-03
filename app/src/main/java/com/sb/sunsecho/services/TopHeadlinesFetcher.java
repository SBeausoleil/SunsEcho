package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.utils.Sources;

public class TopHeadlinesFetcher extends AsyncTask<TopHeadlinesQuery, Void, Article[]> {

    private NewsApiClient client;
    private Sources sources;

    public TopHeadlinesFetcher(NewsApiClient client, Sources sources) {
        this.client = client;
        this.sources = sources;
    }

    @Override
    protected Article[] doInBackground(TopHeadlinesQuery... query) {
        return client.topHeadlines(query[0], sources);
    }
}
