package com.nothing.hunnaz.clustr;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


/**
 * Fragment of the Home Screen. Contains a list of all available events.
 *
 * Created by Zane Clymer on 2/28/2018.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private Location CurrentLocation;
    private DrawerLayout mDrawerLayout;
    private FusedLocationProviderClient mLocation;
    private DatabaseReference mDatabase;

    private ListView mListView;

    private final String TAG = "HomeFragment";
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // DB Instance
        final ArrayList<Event> listItems = new ArrayList<Event>();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("events");
        mDatabase.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of events in datasnapshot
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            listItems.add(child.getValue(Event.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                        // TODO - Fill out
                    }
                });

        // Location
        mLocation = LocationServices.getFusedLocationProviderClient(getActivity());
        getLocation();

        // User
        if(mAuth.getCurrentUser() == null){
            switchIntent(LoginActivity.class);
        }

        // View and rotation
        View v = inflater.inflate(R.layout.fragment_home, container, false);

//        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
//        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
//            switchIntent(HomeActivity.class); // Change this to the landscape version
//        }

        // Navigation Side Menu
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

        // Add Event button
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.addEventButton);
        fab.setOnClickListener(this);

        // List of events on the screen
        mListView = (ListView) v.findViewById(R.id.event_list_view);

        EventAdapter adapter = new EventAdapter(this.getContext(), listItems, CurrentLocation);
        mListView.setAdapter(adapter);

        // Gives items onClickListeners
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showItem(listItems.get(position));
            }
        });

        return v;
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // The callback method gets the result of the request.
            }
        } else {
            // Permission has already been granted
            mLocation.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            CurrentLocation = location;
                            Log.d(TAG, "Current Location\n" + location.toString());
                        }
                    }
                });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied. Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

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
            case R.id.addEventButton:
                switchIntent(AddEventActivity.class);
                break;
        }
    }

    private void logoutUser(){
        UserPrefs.logOutUser(super.getContext());
        switchIntent(WelcomeActivity.class);
    }
}
