package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.ArticlesReceiver;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.utils.Sources;

import java.util.function.Consumer;

public class TopHeadlinesFetcher extends AsyncTask<TopHeadlinesQuery, Void, Article[]> implements Consumer<TopHeadlinesQuery> {

    private NewsApiClient client;
    private Sources sources;
    private ArticlesReceiver receiver;

    public TopHeadlinesFetcher(NewsApiClient client, Sources sources, ArticlesReceiver receiver) {
        this.client = client;
        this.sources = sources;
        this.receiver = receiver;
    }

    @Override
    protected Article[] doInBackground(TopHeadlinesQuery... query) {
        return client.topHeadlines(query[0], sources);
    }

    @Override
    protected void onPostExecute(Article[] articles) {
        receiver.receive(articles);
    }

    @Override
    public void accept(TopHeadlinesQuery query) {
        this.execute(query);
    }
}
