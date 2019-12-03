package com.sb.sunsecho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sb.sunsecho.services.NewsApiClient;
import com.sb.sunsecho.services.SourceFetcher;
import com.sb.sunsecho.utils.Sources;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsApiClient client = new NewsApiClient("1eca1ab431204ff19952f3a874e32f1c");
    }
}
