package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;


/**
 *
 */
public class ItemFragment extends Fragment implements View.OnClickListener{


    private Event event;
    private DatabaseReference mDatabase;
    private static final String TAG = "ItemFragment";
    private FirebaseAuth mAuth;
    private FirebaseUser currentFirebaseUser;

    private void closeFragment(){
        getFragmentManager().popBackStackImmediate();
    }

    public static ItemFragment newInstance(Event e) {
        ItemFragment frag = new ItemFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("title", e.getTitle());
        args.putString("location", e.getAddress());
        args.putInt("capacity", e.getCapacity());
        args.putString("date", e.getDate());
        args.putString("description", e.getDescription());
        args.putDouble("cost", e.getCost());
        args.putInt("age", e.getAge());
        args.putString("creator", e.getCreatorId());
        args.putInt("numAttending", e.getNumCurrentAttending());
        args.putString("key", e.getKey());
        Time t = e.getTime();
        args.putInt("hour", t.get24Hour());
        args.putInt("minute", t.getMinute());
        frag.setArguments(args);
        return frag;
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    private void addItemToDB(final String item, final String table, final String childOfDB){

        mDatabase.child(table).child(childOfDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean alreadyIn = false;
                for (DataSnapshot child : snapshot.getChildren()) {
                    String data = child.getValue().toString();
                    if(data.equals(item)){
                        alreadyIn = true;
                    }
                    Log.d(TAG, child.getValue().toString());
                }
                if(!alreadyIn) {
                    mDatabase.child(table).child(childOfDB).push().setValue(item);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void addUserToGuestList(final String currUser, View v) {
        String eventID = event.getKey();
        String attendingTable = "attending";
        String guestTable = "guestlist";

        addItemToDB(eventID, attendingTable, currUser);
        addItemToDB(currUser, guestTable, eventID);
        event.setNumCurrentAttending(event.getNumCurrentAttending() + 1);
        updateEventInDB();
        String str =  "Open Spots Remaining: " + Integer.toString(event.getCapacity() - event.getNumCurrentAttending());
        TextView text = (TextView) v.findViewById(R.id.eventOpenSpots);
        if(text != null){
            text.setText(str);
        }
    }

    private void removeItemFromDB(final String item, final String table, final String childOfDB){
        mDatabase.child(table).child(childOfDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean alreadyIn = false;
                String key = "";
                for (DataSnapshot child : snapshot.getChildren()) {
                    String data = child.getValue().toString();
                    if(data.equals(item)){
                        alreadyIn = true;
                        key = child.getKey();
                    }
                    Log.d(TAG, child.getValue().toString());
                }
                if(alreadyIn) {
                    mDatabase.child(table).child(childOfDB).child(key).removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void updateEventInDB(){
        String key = event.getKey();

        Log.d(TAG, "update event: " + event.getTitle());
        mDatabase.child("events").child(key).setValue(event);
    }

    private void removeUserFromGuestList(final String currUser, View v) {
        String eventID = event.getKey();
        String attendingTable = "attending";
        String guestTable = "guestlist";

        removeItemFromDB(eventID, attendingTable, currUser);
        removeItemFromDB(currUser, guestTable, eventID);
        event.setNumCurrentAttending(event.getNumCurrentAttending() - 1);
        updateEventInDB();

        String str =  "Open Spots Remaining: " + Integer.toString(event.getCapacity() - event.getNumCurrentAttending());
        TextView text = (TextView) v.findViewById(R.id.eventOpenSpots);
        if(text != null){
            text.setText(str);
        }
    }


    @Override
    public void onClick(View view) {
        final String currUser = currentFirebaseUser.getUid(); // TODO - Make this the current user
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.exitFab:
                closeFragment();
                break;
            case R.id.rsvpButton:
                Button b = (Button) view;
                if(!b.getText().equals("Already RSVP'd! Decline?")) {
                    addUserToGuestList(currUser, v);
                    b.setText("Already RSVP'd! Decline?");
                } else {
                    removeUserFromGuestList(currUser, v);
                    b.setText("RSVP");
                }
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

        String time = "Time: " + event.getTime().toString();
        TextView timeView = (TextView) v.findViewById(R.id.eventTime);
        timeView.setText(time);

        String address = "Address: " + event.getAddress();
        TextView addressView = (TextView) v.findViewById(R.id.eventAddress);
        addressView.setText(address);

        TextView cost = (TextView) v.findViewById(R.id.eventCost);
        double eventCost = event.getCost();
        String costNum;
        if(eventCost == 0){
            costNum = "$FREE";
        }else{
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
            costNum = currencyFormatter.format(eventCost);
        }
        String costStr = "Cost: " + costNum;
        cost.setText(costStr);

        TextView age = (TextView) v.findViewById(R.id.eventAge);
        String ageStr = "Age Required to Attend: " + Integer.toString(event.getAge());
        age.setText(ageStr);

        TextView openSpots = (TextView) v.findViewById(R.id.eventOpenSpots);
        String openSpotsStr = "Open Spots Remaining: " + Integer.toString(event.getCapacity() - event.getNumCurrentAttending());
        openSpots.setText(openSpotsStr);
    }

    private void setText(final Button b, final String currUser, final Event event){
        final String eventID = event.getKey();
        b.setClickable(true);
            mDatabase.child("attending").child(currUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    boolean alreadyIn = false;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String data = child.getValue().toString();
                        if (data.equals(eventID)) {
                            alreadyIn = true;
                        }
                        Log.d(TAG, child.getValue().toString());
                    }
                    if (alreadyIn) {
                        b.setText("Already RSVP'd! Decline?");
                    } else {
                        if(event.getCapacity() - event.getNumCurrentAttending() > 0) {
                            b.setText("RSVP");
                        } else {
                            b.setText("Event Full");
                            b.setClickable(false);
                        }
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
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
        int hour = this.getArguments().getInt("hour");
        int minute = this.getArguments().getInt("minute");

        Time t = new Time(hour, minute);
        event = new Event(title, location, capacity, date, description, cost, age, creator, num, t);
        event.setKey(key);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentFirebaseUser = mAuth.getCurrentUser();

        ConstraintLayout layout = (ConstraintLayout) v.findViewById(R.id.linearLayout4); // id fetch from xml
//        ShapeDrawable rectShapeDrawable = new ShapeDrawable(); // pre defined class
//
//// get paint
//        Paint paint = rectShapeDrawable.getPaint();
//
//// set border color, stroke and stroke width
//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(5); // you can change the value of 5
//        layout.setBackgroundDrawable(rectShapeDrawable);

        layout.setBackgroundColor(Color.GRAY);


        setUpInformation(v);

        FloatingActionButton exit = (FloatingActionButton) v.findViewById(R.id.exitFab);
        exit.setOnClickListener(this);

        final Button rsvp = (Button) v.findViewById(R.id.rsvpButton);
        rsvp.setOnClickListener(this);

        String currUser = currentFirebaseUser.getUid(); // TODO - Needs to be real logged in user
        setText(rsvp, currUser, event);

        return v;
    }
}

