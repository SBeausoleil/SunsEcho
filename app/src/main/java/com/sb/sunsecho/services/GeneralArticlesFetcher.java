package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.GeneralQuery;
import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.utils.Sources;

public class GeneralArticlesFetcher extends AsyncTask<GeneralQuery, Void, Article[]> {

    private NewsApiClient client;
    private Sources sources;

    public GeneralArticlesFetcher(NewsApiClient client, Sources sources) {
        this.client = client;
        this.sources = sources;
    }

    @Override
    protected Article[] doInBackground(GeneralQuery... query) {
        return client.general(query[0], sources);
    }
}