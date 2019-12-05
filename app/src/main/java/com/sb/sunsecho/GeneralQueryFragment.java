package com.sb.sunsecho;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.sb.sunsecho.beans.ApiQuery;
import com.sb.sunsecho.beans.GeneralQuery;
import com.sb.sunsecho.beans.SortBy;
import com.sb.sunsecho.services.SortByService;
import com.sb.sunsecho.utils.Maps;
import com.sb.sunsecho.utils.Spinners;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GeneralQueryFragment extends Fragment implements Supplier<ApiQuery.Builder> {

    private EditText from;
    private EditText to;

    private LinkedHashMap<String, SortBy> localizedSortBy;
    private Spinner sortBy;

    public GeneralQueryFragment() {
    }

    public static GeneralQueryFragment newInstance() {
        return new GeneralQueryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_general_query, container, false);
        from = v.findViewById(R.id.from);
        to = v.findViewById(R.id.to);
        sortBy = v.findViewById(R.id.sort_by);

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
    public GeneralQuery.Builder get() {
        return new GeneralQuery.Builder()
                .withFrom(Instant.parse(from.getText().toString()))
                .withTo(Instant.parse(to.getText().toString()))
                .withSortBy(selectedSort());
    }
}
