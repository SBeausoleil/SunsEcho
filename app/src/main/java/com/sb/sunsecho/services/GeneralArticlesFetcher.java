package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.ArticlesReceiver;
import com.sb.sunsecho.TooBroadQueryException;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.AsyncResult;
import com.sb.sunsecho.beans.GeneralQuery;
import com.sb.sunsecho.utils.Sources;

import java.util.function.Consumer;

public class GeneralArticlesFetcher extends AsyncTask<GeneralQuery, Void, AsyncResult<Article[]>> implements Consumer<GeneralQuery> {

    private NewsApiClient client;
    private Sources sources;
    private ArticlesReceiver receiver;
    private Consumer<TooBroadQueryException> catcher;

    public GeneralArticlesFetcher(NewsApiClient client, Sources sources, ArticlesReceiver receiver, Consumer<TooBroadQueryException> catcher) {
        this.client = client;
        this.sources = sources;
        this.receiver = receiver;
        this.catcher = catcher;
    }

    @Override
    protected AsyncResult<Article[]> doInBackground(GeneralQuery... query) {
        try {
            return new AsyncResult(client.general(query[0], sources));
        } catch (Exception e) {
            AsyncResult<Article[]> res = new AsyncResult<>(null);
            res.setException(e);
            return res;
        }
    }

    @Override
    protected void onPostExecute(AsyncResult<Article[]> articles) {
        if (articles.hasException() && articles.getException() instanceof TooBroadQueryException)
            catcher.accept((TooBroadQueryException) articles.getException());
        else if (articles.hasException()) {
            throw new RuntimeException(articles.getException());
        } else
            receiver.receive(articles.getValue());
    }

    @Override
    public void accept(GeneralQuery query) {
        this.execute(query);
    }
}