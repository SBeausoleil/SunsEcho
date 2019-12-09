package com.sb.sunsecho;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MultiSelectDialog extends Fragment {
    private static final String TAG = MultiSelectDialog.class.getCanonicalName();

    private static final String OPTIONS = "options";

    private String[] options;

    public MultiSelectDialog() {
        // Required empty public constructor
    }

    public static MultiSelectDialog newInstance(String[] options) {
        MultiSelectDialog fragment = new MultiSelectDialog();
        Bundle args = new Bundle();
        args.putStringArray(OPTIONS, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        options = getArguments().getStringArray(OPTIONS);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_select_dialog, container, false);
    }

}
