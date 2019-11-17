package com.example.accessbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UpcomingActivity extends AppCompatActivity implements View.OnClickListener {

    private static String UPCOMING_API = "https://thawing-wave-10019.herokuapp.com/api/get_bookings";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mRequestAssistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        recyclerView = (RecyclerView) findViewById(R.id.upcomingScroll);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(itemDecor);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(UPCOMING_API, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonResponse) {
                mAdapter = new UpcomingAdapter(jsonResponse);
                recyclerView.setAdapter(mAdapter);

                Log.i("TAG", "onSuccess: " + jsonResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.e("TAG", "onFailure: " + errorResponse);
            };
        });

        mRequestAssistance = findViewById(R.id.requestAssistance);
        mRequestAssistance.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.requestAssistance) {
            Intent assistanceIntent = new Intent(this, BookActivity.class);
            startActivity(assistanceIntent);
        }
    }
}
