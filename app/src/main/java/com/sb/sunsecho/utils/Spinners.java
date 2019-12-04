package com.sb.sunsecho.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.LinkedHashMap;

public class Spinners {

    public static void prepareSpinner(Spinner spinner, Context context, LinkedHashMap<String, ?> localizedMap, String emptyString) {
        String[] localizedArr = localizedMap.keySet().toArray(new String[localizedMap.size()]);
        String[] localizedWithFirstEmpty = new String[localizedArr.length + 1];
        localizedWithFirstEmpty[0] = emptyString;
        System.arraycopy(localizedArr, 0, localizedWithFirstEmpty, 1, localizedArr.length);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, localizedWithFirstEmpty);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
