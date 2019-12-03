package com.sb.sunsecho.services;

import android.os.AsyncTask;
import android.util.Log;

import com.sb.sunsecho.SourcesReceiver;
import com.sb.sunsecho.beans.Source;

import java.util.ArrayList;

public class SourceFetcher extends AsyncTask<Void, Void, Source[]> {
    private static final String TAG = SourceFetcher.class.getCanonicalName();

    private NewsApiClient client;
    private SourcesReceiver receiver;

    public SourceFetcher(NewsApiClient client, SourcesReceiver receiver) {
        this.client = client;
        this.receiver = receiver;
    }

    @Override
    protected Source[] doInBackground(Void... voids) {
        return client.sources();
    }

    @Override
    protected void onPostExecute(Source[] sources) {
        receiver.receive(sources);
    }
}
