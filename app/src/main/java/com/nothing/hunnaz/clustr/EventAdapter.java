package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
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

    private FirebaseAuth mAuth;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;
    private User currUser;



    // Constructor
    public EventAdapter(Context context, ArrayList<Event> items, Location location) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLocation = location;

        mAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        currUser = getUser(currentFirebaseUser.getUid());

        filterEvents(mDataSource);
    }

    private User getUser(final String id){
        final User retVal = new User();
        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.getKey().equals(id)){
                    retVal.copyFrom(snapshot.getValue(User.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return retVal;
    }

    private float getLocation(Event e){
        Location eventLoc = e.getLocation(this.mContext);
        float retVal = 100;
        if (mLocation != null && eventLoc != null) {
            float meters = mLocation.distanceTo(e.getLocation(mContext));
            retVal = meters * (float) 0.000621371;
        }
        return retVal;
    }

    private boolean eventValid(Event e, User user){
        Date userBirthday = new Date(user.getBirthday());
        int eventAgeCutoff = e.getAge();

        float distance = getLocation(e);

        return (e.notYetOccurred() && userBirthday.isOlderThan(eventAgeCutoff) && (distance <= 25));
    }

    private void filterEvents(ArrayList<Event> items){
        int i = 0;
        while(currUser != null && i < items.size()){
            Event e = items.get(i);
            if(eventValid(e, currUser)){
                i++;
            } else {
                items.remove(i);
            }
        }
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
        if(eventCost == 0){
            cost = "FREE";
        }else{
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
            cost = currencyFormatter.format(eventCost);
        }
        costTextView.setText(cost);

        // Fill Distance
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String distance = "N/A";
        if (mLocation != null) {
            float meters = mLocation.distanceTo(eventItem.getLocation(mContext));
            float miles = meters * (float) 0.000621371;
            distance = decimalFormat.format(miles) + "mi";
        }
        distanceTextView.setText(distance);

        // Picasso is a open-source library for async pic loading

        return rowView;
    }
}
