package com.sb.sunsecho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.services.CountryCodeService;
import com.sb.sunsecho.services.NewsApiClient;
import com.sb.sunsecho.services.SourceFetcher;
import com.sb.sunsecho.utils.Sources;

public class MainActivity extends AppCompatActivity {

    private Sources sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsApiClient client = new NewsApiClient("1eca1ab431204ff19952f3a874e32f1c");
        SourceFetcher fetcher = new SourceFetcher(client, this::afterReceivingSources);
        fetcher.execute();
    }

    private void afterReceivingSources(Source[] received) {
        this.sources = new Sources(received);
        /*System.out.println("Received " + received.length + " sources.");
        sources.forEach((id, source) -> {
            System.out.println(id + " : " + source.getName() + ", from: " + CountryCodeService.localizedCountryName(source.getCountry(), getResources(), getPackageName()));
        });*/
        Intent i = new Intent(this, SearchActivity.class);
        i.putExtra(SearchActivity.SOURCES, received);
        startActivityForResult(i, SearchActivity.REQUEST_CODE);
    }
}
