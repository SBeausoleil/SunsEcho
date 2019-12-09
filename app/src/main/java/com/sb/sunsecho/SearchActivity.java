package com.sb.sunsecho;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.Article;
import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.services.LanguageCodeService;
import com.sb.sunsecho.utils.Maps;
import com.sb.sunsecho.utils.Sources;
import com.sb.sunsecho.utils.Spinners;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SearchActivity extends AppCompatActivity implements ArticlesReceiver {
    private static final String TAG = SearchActivity.class.getCanonicalName();

    public static final int REQUEST_CODE = 100;
    public static final String SOURCES = "sources";

    private static final int TOP_HEADLINES_INDEX = 0;
    private static final int EVERYTHING_INDEX = 1;

    private Sources sources;
    /**
     * An hashmap of the localized languages supported by the sources.
     * Key: the localized language
     * Value: the languageCode
     */
    private LinkedHashMap<String, String> languages;

    private ApiQueryBuildingInterface searchFragment;

    private Button search;
    private Spinner language;
    private TextInputEditText inTitle;
    private TextInputEditText keywords;
    private TextInputEditText pageSize;
    private TextInputEditText page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sources = Sources.makeSources(getIntent().getParcelableArrayExtra(SOURCES));

        initializeSearchTypeSpinner();
        initializeLanguageSpinner();

        inTitle = findViewById(R.id.in_title);
        keywords = findViewById(R.id.keywords);
        pageSize = findViewById(R.id.page_size);
        page = findViewById(R.id.page);

        search = findViewById(R.id.search);
        search.setOnClickListener(view -> {
            ApiQuery.Builder builder = searchFragment.builder()
                    .withLanguage(getSelectedLanguage())
                    .withInTitle(getInTitle())
                    .withKeywords(getKeywords())
                    .withPageSize(getPageSize())
                    .withPage(getPage());
            searchFragment.articlesAsyncSupplier(this).accept(builder.build());
        });
    }

    private void initializeSearchTypeSpinner() {
        Spinner searchTypeSelector = findViewById(R.id.search_type);
        ArrayAdapter<CharSequence> types = ArrayAdapter.createFromResource(this, R.array.search_types, android.R.layout.simple_spinner_item);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSelector.setAdapter(types);
        searchTypeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case TOP_HEADLINES_INDEX:
                        Log.d(TAG, "Top headlines selected");
                        if (!(searchFragment instanceof TopHeadlinesFragment)) {
                            Log.i(TAG, "New Top Headlines Fragment");
                            searchFragment = TopHeadlinesFragment.newInstance(sources);
                            replaceSearchFragment((Fragment) searchFragment);
                        }
                        break;
                    case EVERYTHING_INDEX:
                        Log.d(TAG, "Everything selected");
                        if (!(searchFragment instanceof GeneralQueryFragment)) {
                            Log.i(TAG, "New General Query Fragment!");
                            searchFragment = GeneralQueryFragment.newInstance(sources.toArray());
                            replaceSearchFragment((Fragment) searchFragment);
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Position " + position + " is not recognized.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void replaceSearchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.search_frame, fragment);
        transaction.commit();
    }

    public String getSelectedLanguage() {
        return languages.get(language.getSelectedItem());
    }

    public String getInTitle() {
        return inTitle.getText().length() != 0 ? inTitle.getText().toString() : null;
    }

    public String getKeywords() {
        return keywords.getText().length() != 0 ? keywords.getText().toString() : null;
    }

    public Integer getPageSize() {
        return pageSize.getText().length() != 0 ? Integer.parseInt(pageSize.getText().toString()) : null;
    }

    public Integer getPage() {
        return page.getText().length() != 0 ? Integer.parseInt(page.getText().toString()) : null;
    }

    private void initializeLanguageSpinner() {
        HashSet<String> languageCodes = sources.languages();
        this.languages = new LinkedHashMap<>(languageCodes.size());
        languageCodes.forEach(language -> this.languages.put(LanguageCodeService.localizedLanguageName(language, getResources(), getPackageName()), language));
        languages = Maps.sort(languages, Map.Entry.comparingByKey());
        this.language = findViewById(R.id.language);
        Spinners.prepareSpinner(language, getApplicationContext(), languages, getString(R.string.no_language));
    }

    @Override
    public void receive(Article[] articles) {
        List<Article> articleList = Arrays.asList(articles);
        articleList.forEach(article -> System.out.println(article));

        Intent i = new Intent(this, MasterActivity.class);
        i.putExtra(MasterActivity.ARTICLES, articles);
        startActivity(i);
        finish();
    }
}
