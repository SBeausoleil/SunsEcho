package com.sb.sunsecho;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.services.LanguageCodeService;
import com.sb.sunsecho.utils.Maps;
import com.sb.sunsecho.utils.Sources;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SearchActivity extends AppCompatActivity {
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

    private Supplier<ApiQuery.Builder> searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        makeSources(getIntent().getParcelableArrayExtra(SOURCES));

        initializeSearchTypeSpinner();
        initializeLanguageSpinner();
    }

    private void makeSources(Parcelable[] sourcesParcel) {
        Source[] arr = new Source[sourcesParcel.length];
        for (int i = 0; i < sourcesParcel.length; i++)
            arr[i] = (Source) sourcesParcel[i];
        this.sources = new Sources(arr);
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
                            searchFragment = GeneralQueryFragment.newInstance();
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

    private void initializeLanguageSpinner() {
        HashSet<String> languageCodes = sources.languages();
        this.languages = new LinkedHashMap<>(languageCodes.size());
        languageCodes.forEach(language -> this.languages.put(LanguageCodeService.localizedLanguageName(language, getResources(), getPackageName()), language));
        languages = Maps.sort(languages, Map.Entry.comparingByKey());

        String[] sortedLanguages = languages.keySet().toArray(new String[languages.size()]);
        Spinner searchTypeSelector = findViewById(R.id.language);
        ArrayAdapter<CharSequence> languagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortedLanguages);
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSelector.setAdapter(languagesAdapter);
    }
}
