package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * This class shows what will be displayed in the event list
 *
 * Created by Zane Clymer on 3/25/2018.
 */

public class EventAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Event> mDataSource;
    private Location mLocation;


    // Constructor
    public EventAdapter(Context context, ArrayList<Event> items, Location location) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLocation = location;
    }

    // Lets ListView know how many items to display.
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    // Returns an item at the ListView's position.
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    // Defines a unique ID for each item in the list
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Creates a view to be used as a row in the list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_event, parent, false);

        // Get the info in the cards
        TextView titleTextView = (TextView) rowView.findViewById(R.id.event_item_title);
        TextView dateTextView = (TextView) rowView.findViewById(R.id.event_item_date);
        TextView costTextView = (TextView) rowView.findViewById(R.id.event_item_cost);
        TextView distanceTextView = (TextView) rowView.findViewById(R.id.event_item_distance);

        // Get an Event
        Event eventItem = (Event) getItem(position);

        // Fill Title
        titleTextView.setText(eventItem.getTitle());

        // Fill Date
        dateTextView.setText(eventItem.getDate());

        // Fill Cost
        double eventCost = eventItem.getCost();
        String cost;
        if (eventCost == 0) {
            cost = "$FREE";
        } else {
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
            cost = currencyFormatter.format(eventCost);
        }
        costTextView.setText(cost);

        // Fill Distance
//        String distance = "1.24" + "mi";// TODO - Fix to be dynamic
        String distance = "N/A";
        if (mLocation != null) {
            float meters = mLocation.distanceTo(eventItem.getLocation(mContext));
            float miles = meters * (float) 0.000621371;
            distance = Float.toString(miles) + "mi";
        }
        distanceTextView.setText(distance);

        // Picasso is a open-source library for async pic loading

        return rowView;
    }
}
