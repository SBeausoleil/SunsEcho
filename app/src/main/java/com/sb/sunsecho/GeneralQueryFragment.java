package com.sb.sunsecho;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.sb.sunsecho.beans.GeneralQuery;
import com.sb.sunsecho.beans.SortBy;
import com.sb.sunsecho.beans.Source;
import com.sb.sunsecho.services.GeneralArticlesFetcher;
import com.sb.sunsecho.services.NewsApiClient;
import com.sb.sunsecho.services.SortByService;
import com.sb.sunsecho.utils.Maps;
import com.sb.sunsecho.utils.Sources;
import com.sb.sunsecho.utils.Spinners;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import androidx.fragment.app.Fragment;

public class GeneralQueryFragment extends Fragment implements ApiQueryBuildingInterface<GeneralQuery> {
    private static final String TAG = GeneralQueryFragment.class.getCanonicalName();

    private static final String SOURCES = "sources";

    private TextInputEditText inTitle;

    private Instant fromInstant;
    private EditText from;

    private Instant toInstant;
    private EditText to;

    private LinkedHashMap<String, SortBy> localizedSortBy;
    private Spinner sortBy;

    private Sources sources;

    public GeneralQueryFragment() {
    }

    public static GeneralQueryFragment newInstance(Source[] sources) {
        GeneralQueryFragment fragment = new GeneralQueryFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(SOURCES, sources);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sources = Sources.makeSources(getArguments().getParcelableArray(SOURCES));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_general_query, container, false);
        inTitle = v.findViewById(R.id.in_title);
        from = v.findViewById(R.id.from);
        to = v.findViewById(R.id.to);
        sortBy = v.findViewById(R.id.sort_by);

        from.setOnClickListener((view) -> {
            DatePickerFragment picker = new DatePickerFragment(fromInstant, (instant -> {
                fromInstant = instant;
                from.setText(instant.atZone(ZoneId.systemDefault()).toLocalDate().toString());
            }));
            picker.show(getActivity().getSupportFragmentManager(), "FROM");
        });

        to.setOnClickListener((view) -> {
            DatePickerFragment picker = new DatePickerFragment(toInstant, (instant -> {
                toInstant = instant;
                to.setText(instant.atZone(ZoneId.systemDefault()).toLocalDate().toString());
            }));
            picker.show(getActivity().getSupportFragmentManager(), "TO");
        });

        initializeSortBy();
        return v;
    }

    private void initializeSortBy() {
        SortBy[] arr = SortBy.values();
        localizedSortBy = new LinkedHashMap<>(arr.length);
        for (SortBy s : arr)
            localizedSortBy.put(SortByService.localize(s, getResources()), s);
        localizedSortBy = Maps.sort(localizedSortBy, Map.Entry.comparingByKey());
        Spinners.prepareSpinner(sortBy, getContext(), localizedSortBy, getResources().getString(R.string.no_sort_by));
    }

    public SortBy selectedSort() {
        return localizedSortBy.get(sortBy.getSelectedItem());
    }

    @Override
    public GeneralQuery.Builder builder() {
        return new GeneralQuery.Builder()
                .withInTitle(getInTitle())
                .withFrom(getFrom())
                .withTo(getTo())
                .withSortBy(selectedSort());
    }

    public String getInTitle() {
        return inTitle.getText().length() != 0 ? inTitle.getText().toString() : null;
    }

    public Instant getFrom() {
        return from.getText().length() != 0 ? LocalDate.parse(from.getText()).atStartOfDay(ZoneId.systemDefault()).toInstant() : null;
    }

    public Instant getTo() {
        return to.getText().length() != 0 ?  LocalDate.parse(to.getText()).atStartOfDay(ZoneId.systemDefault()).toInstant() : null;
    }

    @Override
    public Consumer<GeneralQuery> articlesAsyncSupplier(ArticlesReceiver receiver, Consumer<TooBroadQueryException> catcher) {
        return new GeneralArticlesFetcher(NewsApiClient.getInstance(), sources, receiver, catcher);
    }
}
