package com.sb.sunsecho;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.Category;
import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.services.CategoryService;
import com.sb.sunsecho.services.CountryCodeService;
import com.sb.sunsecho.services.NewsApiClient;
import com.sb.sunsecho.services.TopHeadlinesFetcher;
import com.sb.sunsecho.utils.Maps;
import com.sb.sunsecho.utils.Sources;
import com.sb.sunsecho.utils.Spinners;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import androidx.fragment.app.Fragment;

public class TopHeadlinesFragment extends Fragment implements ApiQueryBuildingInterface<TopHeadlinesQuery> {
    private static final String TAG = TopHeadlinesQuery.class.getCanonicalName();

    private static final String COUNTRIES = "countries";
    private static final String SOURCES = "sources";

    private String[] countries;
    /**
     * Localized countries.
     * Key: Localized name
     * Value: Country code
     */
    private LinkedHashMap<String, String> localizedCountries;
    private Spinner country;

    private LinkedHashMap<String, Category> localizedCategories;
    private Spinner category;

    private Sources sources;

    public TopHeadlinesFragment() {
    }

    public static TopHeadlinesFragment newInstance(Sources sources) {
        HashSet<String> countriesSet = new HashSet<>();
        sources.values().forEach(source -> countriesSet.add(source.getCountry()));
        String[] countries = countriesSet.toArray(new String[countriesSet.size()]);

        TopHeadlinesFragment fragment = new TopHeadlinesFragment();
        Bundle args = new Bundle();
        args.putStringArray(COUNTRIES, countries);
        args.putParcelableArray(SOURCES, sources.toArray());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countries = getArguments().getStringArray(COUNTRIES);
        sources = Sources.makeSources(getArguments().getParcelableArray(SOURCES));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_top_headlines, container, false);
        initializeCountriesSpinner(v);
        initializeCategoriesSpinner(v);
        return v;
    }

    private void initializeCategoriesSpinner(View v) {
        category = v.findViewById(R.id.category);
        Category[] arr = Category.values();
        localizedCategories = new LinkedHashMap<>(arr.length);
        for (Category category : arr)
            localizedCategories.put(CategoryService.localize(category, getResources()), category);
        localizedCategories = Maps.sort(localizedCategories, Map.Entry.comparingByValue());
        Spinners.prepareSpinner(category, getContext(), localizedCategories, getString(R.string.no_category));
    }

    private void initializeCountriesSpinner(View v) {
        country = v.findViewById(R.id.country);
        localizedCountries = new LinkedHashMap<>(countries.length);
        for (String c : countries)
            localizedCountries.put(CountryCodeService.localizedCountryName(c, getResources(), getActivity().getPackageName()), c);
        localizedCountries = Maps.sort(localizedCountries, Map.Entry.comparingByKey());
        Spinners.prepareSpinner(country, getContext(), localizedCountries, getString(R.string.no_country));
    }

    public String selectedCountryCode() {
        return localizedCountries.get(country.getSelectedItem());
    }

    public Category selectedCategory() {
        return localizedCategories.get(category.getSelectedItem());
    }

    @Override
    public TopHeadlinesQuery.Builder builder() {
        return new TopHeadlinesQuery.Builder()
                .withCountry(selectedCountryCode())
                .withCategory(selectedCategory());
    }

    @Override
    public Consumer<TopHeadlinesQuery> articlesAsyncSupplier(ArticlesReceiver receiver, Consumer<TooBroadQueryException> catcher) {
        return new TopHeadlinesFetcher(NewsApiClient.getInstance(), sources, receiver, catcher);
    }
}
