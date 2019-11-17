package com.example.accessbook;

import androidx.fragment.app.FragmentActivity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class BookActivity extends FragmentActivity implements View.OnClickListener {

    private static String BOOKING_API = "https://thawing-wave-10019.herokuapp.com/api/book";

    private TextInputEditText departureStation;
    private TextInputEditText arrivalStation;
    private TextInputEditText phoneNumber;

    private TextView departureDate;
    private TextView departureTime;
    private TextView arrivalDate;
    private TextView arrivalTime;

    private CheckBox visibility;
    private CheckBox hearing;

    private Button mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        departureStation = findViewById(R.id.departureStation);
        arrivalStation = findViewById(R.id.arrivalStation);
        phoneNumber = findViewById(R.id.phoneNumber);

        departureDate = findViewById(R.id.departureDate);
        departureTime = findViewById(R.id.departureTime);
        arrivalDate = findViewById(R.id.arrivalDate);
        arrivalTime = findViewById(R.id.arrivalTime);

        visibility = findViewById(R.id.visibility);
        hearing = findViewById(R.id.hearing);

        mConfirm = findViewById(R.id.confirm);
        mConfirm.setOnClickListener(this);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("dateButton", view.getId());
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View view) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("timeButton", view.getId());
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirm) {
            AsyncHttpClient client = new AsyncHttpClient();

            JSONObject json = new JSONObject();
            StringEntity entity = null;
            String note = "";
            if (visibility.isChecked()) {
                note += getString(R.string.visibility);
            }
            if (hearing.isChecked()) {
                note += ", " + getString(R.string.hearing);
            }
            try {
                json.put("departure", departureStation.getText());
                json.put("departure", departureStation.getText());
                json.put("arrival", arrivalStation.getText());
                json.put("departure_time", departureDate.getText() + " " + departureTime.getText());
                json.put("arrival_time", arrivalDate.getText() + " " + arrivalTime.getText());
                json.put("contact_number", phoneNumber.getText());
                json.put("note", note);
                entity = new StringEntity(json.toString());
            } catch (Exception e) {
                Log.i("TAG", "JSON exception");
            }

            client.post(this, BOOKING_API, entity, "application/json", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                    String jsonResponse = obj.toString();
                    Log.i("TAG", "onSuccess: " + jsonResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText( BookActivity.this, R.string.submitted, Toast.LENGTH_LONG).show();
                    Log.e("TAG", "onFailure: " + responseString);
                };
            });
        }
    }
}
