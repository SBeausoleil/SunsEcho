package com.sb.sunsecho;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.UUID;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = SearchActivity.class.getCanonicalName();

    public static final int REQUEST_CODE = 100;
    public static final String SOURCES = "sources";

    private static final int TOP_HEADLINES_INDEX = 0;
    private static final int EVERYTHING_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Spinner searchTypeSelector = findViewById(R.id.search_type);
        ArrayAdapter<CharSequence> types = ArrayAdapter.createFromResource(this, R.array.search_types, android.R.layout.simple_spinner_item);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSelector.setAdapter(types);
        searchTypeSelector.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case TOP_HEADLINES_INDEX:
                Log.d(TAG, "Top headlines selected");
                break;
            case EVERYTHING_INDEX:
                Log.d(TAG, "Everything selected");
                break;
            default:
                throw new IllegalArgumentException("Position " + position + " is not recognized.");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
