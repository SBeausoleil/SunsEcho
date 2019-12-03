package com.sb.sunsecho;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sb.sunsecho.beans.TopHeadlinesQuery;
import com.sb.sunsecho.services.CountryCodeService;
import com.sb.sunsecho.utils.Sources;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import androidx.fragment.app.Fragment;

public class TopHeadlinesFragment extends Fragment implements Supplier<TopHeadlinesQuery> {
    private static final String TAG = TopHeadlinesQuery.class.getCanonicalName();

    private static final String COUNTRIES = "countries";

    private String[] countries;
    /**
     * Localized countries.
     * Key: Localized name
     * Value: Country code
     */
    private LinkedHashMap<String, String> localizedCountries;

    public TopHeadlinesFragment() {
    }

    public static TopHeadlinesFragment newInstance(Sources sources) {
        HashSet<String> countriesSet = new HashSet<>();
        sources.values().forEach(source -> countriesSet.add(source.getCountry()));
        String[] countries = countriesSet.toArray(new String[countriesSet.size()]);

        TopHeadlinesFragment fragment = new TopHeadlinesFragment();
        Bundle args = new Bundle();
        args.putStringArray(COUNTRIES, countries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countries = getArguments().getStringArray(COUNTRIES);
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
        
        Spinner categoriesSpinner = v.findViewById(R.id.category);
    }

    private void initializeCountriesSpinner(View v) {
        localizedCountries = new LinkedHashMap<>(countries.length);
        for (String c : countries)
            localizedCountries.put(CountryCodeService.localizedCountryName(c, getResources(), null), c);
        sortCountriesByLocalization();
        String[] localized = localizedCountries.keySet().toArray(new String[localizedCountries.size()]);

        Spinner countriesSpinner = v.findViewById(R.id.country);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, localized);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countriesSpinner.setAdapter(adapter);
    }

    private void sortCountriesByLocalization() {
        localizedCountries = localizedCountries.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    @Override
    public TopHeadlinesQuery get() {
        return null; // TODO
    }
}
