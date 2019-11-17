package com.example.accessbook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.MyViewHolder> {

    private JSONArray upcoming;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public MaterialCardView cardView;
        public MyViewHolder(MaterialCardView v) {
            super(v);
            cardView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UpcomingAdapter(JSONArray upcoming) {
        this.upcoming = upcoming;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UpcomingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        MaterialCardView v = (MaterialCardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_card, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        TextView departureStation = holder.cardView.findViewById(R.id.departureStation);
        TextView arrivalStation = holder.cardView.findViewById(R.id.arrivalStation);

        TextView departureDate = holder.cardView.findViewById(R.id.departureDate);
        TextView arrivalDate = holder.cardView.findViewById(R.id.arrivalDate);

        TextView departureTime = holder.cardView.findViewById(R.id.departureTime);
        TextView arrivalTime = holder.cardView.findViewById(R.id.arrivalTime);

        String date;
        String time;

        try {
            JSONObject obj = upcoming.getJSONObject(position);
            JSONObject fields = obj.getJSONObject("fields");
            departureStation.setText(fields.getString("departure"));
            arrivalStation.setText(fields.getString("arrival"));

            date = fields.getString("departure_time").substring(0, 10);
            time = fields.getString("departure_time").substring(11, 16);
            departureDate.setText(date);
            departureTime.setText(time);

            date = fields.getString("arrival_time").substring(0, 10);
            time = fields.getString("arrival_time").substring(11, 16);
            arrivalDate.setText(date);
            arrivalTime.setText(time);
        } catch (Exception e) {

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return upcoming.length();
    }
}