package com.sb.sunsecho;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.services.CountryCodeService;
import com.sb.sunsecho.services.NewsApiClient;
import com.sb.sunsecho.services.SourceFetcher;
import com.sb.sunsecho.utils.Sources;

public class MainActivity extends AppCompatActivity {

    private Source[] sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsApiClient client = new NewsApiClient("1eca1ab431204ff19952f3a874e32f1c");
        SourceFetcher fetcher = new SourceFetcher(client, this::afterReceivingSources);
        fetcher.execute();
    }

    private void afterReceivingSources(Source[] received) {
        this.sources = received;
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra(SearchActivity.SOURCES, received);
        startActivityForResult(i, SearchActivity.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case SearchActivity.REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Parcelable[] arr = data.getParcelableArrayExtra(SearchActivity.RESULT);
                    Article[] articles = new Article[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        articles[i] = (Article) arr[i];
                    }

                    Intent i = new Intent(this, MasterActivity.class);
                    i.putExtra(MasterActivity.ARTICLES, articles);
                    i.putExtra(MasterActivity.SOURCES, sources);
                    startActivity(i);
                    finish();
                } else throw new RuntimeException("Search activity returned with result \"not ok\".");
                break;
        }
    }
}
