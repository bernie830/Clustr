package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nothing.hunnaz.clustr.EventDB.Event;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener{


    private boolean isLoggedIn = false;

    private DatabaseReference dbEvents;

    private void switchToLogin(){
        Intent myIntent = new Intent(this.getContext(), LoginActivity.class);
        startActivity(myIntent);
    }

    private void switchToAccount(){
        Intent myIntent = new Intent(this.getContext(), AccountActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.loginButton:
                if(isLoggedIn) {
                    switchToAccount();
                } else {
                    switchToLogin();
                }
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        isLoggedIn = UserPrefs.isLoggedIn(this.getContext());
        if(isLoggedIn){
            Button loginButton = (Button) v.findViewById(R.id.loginButton);
            String accountText = "Account Home";
            loginButton.setText(accountText);
        }

        Button btnAdd = (Button) v.findViewById(R.id.loginButton);
        btnAdd.setOnClickListener(this);

        dbEvents = FirebaseDatabase.getInstance().getReference("events");
        dbEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//              System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        final ArrayList<Event> allEvents = new ArrayList<>();

        dbEvents.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                allEvents.add(dataSnapshot.getValue(Event.class));
                System.out.println("%%%%%%%" + allEvents.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Event event = dataSnapshot.getValue(Event.class);
                System.out.println("***************");
                System.out.println(event.toString());
                System.out.println("***************");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        dbEvents.push().setValue(new Event("Added from code", "your mom's house", 7, "022818", "fun time", 25, 18, "-A"));

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
