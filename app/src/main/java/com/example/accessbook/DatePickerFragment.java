package com.example.accessbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        int id = getArguments().getInt("dateButton");

        Button mDateButton;
        TextView mDateText;
        if (id == R.id.pickDepartureDate) {
            mDateButton = getActivity().findViewById(R.id.pickDepartureDate);
            mDateText = getActivity().findViewById(R.id.departureDate);

        } else {
            mDateButton = getActivity().findViewById(R.id.pickArrivalDate);
            mDateText = getActivity().findViewById(R.id.arrivalDate);
        }

        mDateText.setText(getString(R.string.date, year, month, day));
        mDateButton.setText(R.string.change_date);
    }
}