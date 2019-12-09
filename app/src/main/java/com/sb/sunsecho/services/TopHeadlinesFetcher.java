package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.ArticlesReceiver;
import com.sb.sunsecho.TooBroadQueryException;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.AsyncResult;
import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.utils.Sources;

import java.util.function.Consumer;

public class TopHeadlinesFetcher extends AsyncTask<TopHeadlinesQuery, Void, AsyncResult<Article[]>> implements Consumer<TopHeadlinesQuery> {

    private NewsApiClient client;
    private Sources sources;
    private ArticlesReceiver receiver;
    private Consumer<TooBroadQueryException> catcher;

    public TopHeadlinesFetcher(NewsApiClient client, Sources sources, ArticlesReceiver receiver, Consumer<TooBroadQueryException> catcher) {
        this.client = client;
        this.sources = sources;
        this.receiver = receiver;
        this.catcher = catcher;
    }

    @Override
    protected AsyncResult<Article[]> doInBackground(TopHeadlinesQuery... query) {
        try {
            return new AsyncResult<>(client.topHeadlines(query[0], sources));
        } catch (TooBroadQueryException e) {
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
    public void accept(TopHeadlinesQuery query) {
        this.execute(query);
    }
}
