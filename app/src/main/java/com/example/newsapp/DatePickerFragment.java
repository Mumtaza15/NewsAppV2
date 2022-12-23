package com.example.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        DetailData activity = (DetailData) getActivity();
//        activity.processDatePickerResult(dayOfMonth, month, year);
        SignUp activity0 = (SignUp) getActivity();
        activity0.processDatePickerResult0(dayOfMonth, month, year);
    }

//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//        SignUp activity = (SignUp) getActivity();
//        activity.processDatePickerResult(dayOfMonth, month, year);
//    }

}