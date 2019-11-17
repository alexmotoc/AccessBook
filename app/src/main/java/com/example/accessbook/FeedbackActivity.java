package com.example.accessbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    public static String FEEDBACK_API = "https://thawing-wave-10019.herokuapp.com/api/rate";

    private RatingBar mRating;

    private SeekBar mAssistance;
    private SeekBar mFacility;
    private SeekBar mNavigation;

    private TextInputEditText mImprovement;

    private Button mSendFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        mRating = findViewById(R.id.rating);

        mAssistance = findViewById(R.id.assistance);
        mFacility = findViewById(R.id.facility);
        mNavigation = findViewById(R.id.navigation);

        mImprovement = findViewById(R.id.improvement);

        mSendFeedback = findViewById(R.id.sendFeedback);
        mSendFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.sendFeedback) {
            AsyncHttpClient client = new AsyncHttpClient();

            JSONObject json = new JSONObject();
            StringEntity entity = null;
            try {
                json.put("overall", mRating.getRating() * 2);
                json.put("assistance", mAssistance.getProgress());
                json.put("suitability", mFacility.getProgress());
                json.put("navigability", mNavigation.getProgress());
                json.put("improvements", mImprovement.getText());
                entity = new StringEntity(json.toString());
            } catch (Exception e) {
                Log.i("TAG", "JSON exception");
            }

            client.post(this, FEEDBACK_API, entity, "application/json", new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject obj) {
                    String jsonResponse = obj.toString();
                    Log.i("TAG", "onSuccess: " + jsonResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText( FeedbackActivity.this, R.string.sent, Toast.LENGTH_LONG).show();

                    Log.d("TAG" , "onFailure : " + responseString);
                }
            });
        }
    }
}
