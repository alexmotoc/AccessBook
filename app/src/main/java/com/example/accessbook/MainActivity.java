package com.example.accessbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBook;
    private Button mUpcoming;
    private Button mFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBook = findViewById(R.id.bookButton);
        mBook.setOnClickListener(this);

        mUpcoming = findViewById(R.id.upcomingButton);
        mUpcoming.setOnClickListener(this);

        mFeedback = findViewById(R.id.feedbackButton);
        mFeedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bookButton:
                Intent bookIntent = new Intent(this, BookActivity.class);
                startActivity(bookIntent);
                break;
            case R.id.upcomingButton:
                Intent upcomingIntent = new Intent(this, UpcomingActivity.class);
                startActivity(upcomingIntent);
                break;
            case R.id.feedbackButton:
                Intent feedbackIntent = new Intent(this, FeedbackActivity.class);
                startActivity(feedbackIntent);
                break;
            default:
                break;
        }
    }
}
