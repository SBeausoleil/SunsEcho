package com.sb.sunsecho.services;

import android.os.AsyncTask;

import com.sb.sunsecho.beans.Source;

public class SourceFetcher extends AsyncTask<Void, Void, Source[]> {

    private NewsApiClient client;

    public SourceFetcher(NewsApiClient client) {
        this.client = client;
    }

    @Override
    protected Source[] doInBackground(Void... voids) {
        return client.sources();
    }
}
