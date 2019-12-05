package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.ArticlesReceiver;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.GeneralQuery;
import com.sb.sunsecho.utils.Sources;

import java.util.function.Consumer;

public class GeneralArticlesFetcher extends AsyncTask<GeneralQuery, Void, Article[]> implements Consumer<GeneralQuery> {

    private NewsApiClient client;
    private Sources sources;
    private ArticlesReceiver receiver;

    public GeneralArticlesFetcher(NewsApiClient client, Sources sources, ArticlesReceiver receiver) {
        this.client = client;
        this.sources = sources;
        this.receiver = receiver;
    }

    @Override
    protected Article[] doInBackground(GeneralQuery... query) {
        return client.general(query[0], sources);
    }

    @Override
    protected void onPostExecute(Article[] articles) {
        receiver.receive(articles);
    }

    @Override
    public void accept(GeneralQuery query) {
        this.execute(query);
    }
}