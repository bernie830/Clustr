package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 */
public class ItemFragment extends Fragment implements View.OnClickListener{


    private Event event;
    private DatabaseReference mDatabase;
    private static final String TAG = "ItemFragment";

    private void closeFragment(){
        getFragmentManager().popBackStackImmediate();
    }

    public static ItemFragment newInstance(Event e) {
        ItemFragment frag = new ItemFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("title", e.getTitle());
        args.putString("location", e.getLocation());
        args.putInt("capacity", e.getCapacity());
        args.putString("date", e.getDate());
        args.putString("description", e.getDescription());
        args.putDouble("cost", e.getCost());
        args.putInt("age", e.getAge());
        args.putString("creator", e.getCreatorId());
        args.putInt("numAttending", e.getNumCurrentAttending());
        args.putString("key", e.getKey());
        frag.setArguments(args);
        return frag;
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    private void addUserToGuestList(){

        final String currUser = "TestUser";
        String eventID = event.getKey();
        Log.d(TAG, "rsvp event: " + event.getTitle());

        final ArrayList<String> allEvents = new ArrayList<>();

        DatabaseReference ref = mDatabase.child("attending").child(currUser);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try{
                    Iterator<DataSnapshot> dataSnapshots = snapshot.getChildren().iterator();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        String str = dataSnapshotChild.getValue(String.class);
                        if(!allEvents.contains(str)){
                            allEvents.add(str);
                        }
                    }
                } catch (Throwable e) {
                    Log.d(TAG, "Error getting events");
                }
                if(allEvents.size() > 0){
                    Log.d(TAG, "Got events");
                    mDatabase.child("attending").child(currUser).setValue(allEvents);
                }
            }
            @Override public void onCancelled(DatabaseError error) { }
        });
        if(!allEvents.contains(eventID)){
            allEvents.add(eventID);
        }
        mDatabase.child("attending").child(currUser).setValue(allEvents);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.exitFab:
                closeFragment();
                break;
            case R.id.rsvpButton:
                addUserToGuestList();
        }
    }

    private void setUpInformation(View v){
        TextView title = (TextView) v.findViewById(R.id.eventName);
        title.setText(event.getTitle());
        TextView desc = (TextView) v.findViewById(R.id.eventDescription);
        desc.setText(event.getDescription());
        TextView date = (TextView) v.findViewById(R.id.eventDate);
        Date day = new Date(event.getDate());
        String dateStr = "Date: " + day.toString();
        date.setText(dateStr);
        TextView cost = (TextView) v.findViewById(R.id.eventCost);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String costStr = decimalFormat.format(event.getCost());
        costStr = "Cost: " + costStr;
        cost.setText(costStr);
        TextView age = (TextView) v.findViewById(R.id.eventAge);
        String ageStr = "Age Required to Attend: " + Integer.toString(event.getAge());
        age.setText(ageStr);
        TextView openSpots = (TextView) v.findViewById(R.id.eventOpenSpots);
        String openSpotsStr = "Open Spots Remaining: " + Integer.toString(event.getCapacity() - event.getNumCurrentAttending());
        age.setText(openSpotsStr);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        String title = this.getArguments().getString("title");
        String location = this.getArguments().getString("location");
        int capacity = this.getArguments().getInt("capacity");
        String date = this.getArguments().getString("date");
        String description = this.getArguments().getString("description");
        double cost = this.getArguments().getDouble("cost");
        int age = this.getArguments().getInt("age");
        String creator = this.getArguments().getString("creator");
        int num = this.getArguments().getInt("numAttending");
        String key = this.getArguments().getString("key");

        event = new Event(title, location, capacity, date, description, cost, age, creator, num);
        event.setKey(key);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //ImageView image = (ImageView) v.findViewById(R.id.eventImage);
        //image.setImageResource(R.mipmap.missing_img_round);

        setUpInformation(v);

        FloatingActionButton exit = (FloatingActionButton) v.findViewById(R.id.exitFab);
        exit.setOnClickListener(this);

        Button rsvp = (Button) v.findViewById(R.id.rsvpButton);
        rsvp.setOnClickListener(this);

        return v;
    }
}

