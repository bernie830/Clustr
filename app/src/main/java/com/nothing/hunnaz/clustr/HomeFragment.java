package com.nothing.hunnaz.clustr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
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
 * Created by Zane Clymer on 2/28/2018.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private DatabaseReference dbEvents;
    private FirebaseAuth mAuth;

    private DrawerLayout mDrawerLayout;

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);

        startActivity(myIntent);
    }

    private void showItem(Event event){

        FragmentManager manager = getFragmentManager();
        ItemFragment itemFragment = ItemFragment.newInstance(event);
        FragmentTransaction transaction = manager.beginTransaction().add(this.getId(), itemFragment);
        transaction.addToBackStack("added");
        transaction.commit();

    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.testShow:
                // TODO - This needs to be the event that was clicked
                Event event = new Event("Fake Event","Here",1,"11/11/11","This is a cool event for being fake.",1,1,"Me", 12);
                event.setKey("LA8thisisatestkey93w94");
                showItem(event);
                break;
            case R.id.addEventButton:
                switchIntent(AddEventActivity.class);
                break;
        }
    }

    private void logoutUser(){
        UserPrefs.logOutUser(super.getContext());
        switchIntent(WelcomeActivity.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Firebase
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){
            switchIntent(LoginActivity.class);
        }
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(HomeActivity.class); // Change this to the landscape version
        }

        v.findViewById(R.id.testShow).setOnClickListener(this);

            mDrawerLayout = v.findViewById(R.id.drawer_layout);

            NavigationView navigationView = v.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                            // set item as selected to persist highlight
                            menuItem.setChecked(true);
                            // close drawer when item is tapped
                            mDrawerLayout.closeDrawers();

                            // Add code here to update the UI based on the item selected
                            // For example, swap UI fragments here
                            switch(menuItem.getItemId()){
                                case R.id.nav_account:
                                    switchIntent(AccountActivity.class);
                                    break;
                                case R.id.nav_logout:
                                    logoutUser();
                                    break;
                                case R.id.nav_exit:
                                    getActivity().moveTaskToBack(true);
                                    getActivity().finish();
                                    break;
                            }



                            return true;
                        }
                    }
            );

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
            FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.addEventButton);
            fab.setOnClickListener(this);



        return v;
    }

}
