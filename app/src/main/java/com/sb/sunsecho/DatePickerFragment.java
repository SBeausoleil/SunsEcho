package com.sb.sunsecho;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Consumer;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = DatePickerFragment.class.getCanonicalName();

    private Instant instant;
    private Consumer<Instant> onDateSet;

    public DatePickerFragment(Instant defaultValue, Consumer<Instant> onDateSet) {
        this.instant = defaultValue;
        this.onDateSet = onDateSet;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (instant != null) {
            ZonedDateTime local = instant.atZone(ZoneId.systemDefault());
            return new DatePickerDialog(getActivity(), this, local.getYear(), local.getMonth().getValue() - 1, local.getDayOfMonth());
        }
        DatePickerDialog dialog = new DatePickerDialog(getContext());
        dialog.setOnDateSetListener(this);
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.i(TAG, "onDateSet()");
        this.instant = ZonedDateTime.of(LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0), ZoneId.systemDefault()).toInstant();
        this.onDateSet.accept(instant);
    }
}
