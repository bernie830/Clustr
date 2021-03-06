package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 *
 */
public class AddEventFragment extends Fragment implements View.OnClickListener {

    EditText name;
    EditText description;
    EditText cost;
    EditText age;
    EditText date;
    EditText capacity;
    EditText address;
    TimePicker time;
    TextView information;

    private FirebaseAuth mAuth;
    private FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabase;

    private static final String TAG = "AddEventFragment";

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    private void addEventToDatabase(Event event){
        Log.d(TAG, "create event: " + event.getTitle());
        String name = mDatabase.child("events").push().getKey();
        event.setKey(name);
        mDatabase.child("events").child(name).setValue(event);
    }

    private static String validateName(String name){
        String retVal = "";
        int len = name.length();
        boolean b = (len == 0);
        if(b) { retVal = "ERROR: A name must be entered for the event"; }

        return retVal;
    }

    private static String validateCost(String cost){
        String retVal = "";
        double newCost = 0.0;
        try {
            newCost = Double.parseDouble(cost);
        } catch (NumberFormatException e){
            retVal = "ERROR: The entered cost is not a valid price";
        }
        boolean b = (newCost < 0);
        if(b) { retVal = "ERROR: A cost must be entered for the event and must be greater than $0.00"; }
        return retVal;    }

    private static String validateAge(String age){
        String retVal = "";
        int ageNum = 1;
        try {
            ageNum = Integer.parseInt(age);
        } catch (NumberFormatException e){
            retVal = "ERROR: The entered age is not a valid age";
        }
        if(ageNum < 1 || ageNum > 100){
            retVal = "ERROR: The age requirement must be between 1 and 100";
        }
        return retVal;
    }

    private static String validateDate(String date){
        String retVal = "";
        Date day = new Date(date);
        if(!day.confirmDate(true)){
            retVal = "ERROR: The entered date is not valid";
        }
        return retVal;    }

    private static String validateCap(String capacity){
        String retVal = "";
        int capNum = 1;
        try {
            capNum = Integer.parseInt(capacity);
        } catch (NumberFormatException e){
            retVal = "ERROR: The entered capacity is not a valid capacity";
        }
        boolean b = (capNum < 1);
        if(b) { retVal = "ERROR: The age requirement must be one or more"; }
        return retVal;
    }

    private String validateAddress(String address){
        String retVal = "";
        if (address.length() == 0) {
            retVal = "ERROR: An address must be entered for the event";
        }
        Event tempEvent = new Event("", address, 0, "", "", 0, 0, "", 0, new Time());
        Location loc = tempEvent.getLocation(getContext());
        if(loc.getLatitude() == 0.0 && loc.getLongitude() == 0.0){
            retVal = "The entered address was invalid. Must be a valid location.";
        }
        return retVal;    }

    private static String validateUser(String user){
        String retVal = "";
        if(user == null || user.length() == 0) {
            retVal = "ERROR: Must be logged in";
        }
        return retVal;    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                switchIntent(HomeActivity.class);
                break;
            case R.id.doneButton:
                String nameStr = name.getText().toString();
                String descriptionStr = description.getText().toString();
                String costStr = cost.getText().toString();
                String ageStr = age.getText().toString();
                String dateStr = date.getText().toString();
                String capacityStr = capacity.getText().toString();
                String addressStr = address.getText().toString();
                String errorMessage = validateName(nameStr);
                int hour = 0;
                int minute = 0;
                if (Build.VERSION.SDK_INT >= 23 ) {
                    hour = time.getHour();
                    minute = time.getMinute();
                } else {
                    hour = time.getCurrentHour();
                    minute = time.getCurrentMinute();
                }
                Time t = new Time(hour, minute);
                if(errorMessage.length() == 0){
                    errorMessage = validateAddress(addressStr);
                }
                if(errorMessage.length() == 0){
                    errorMessage = validateDate(dateStr);
                }
                if(errorMessage.length() == 0){
                    errorMessage = validateCost(costStr);
                }
                if(errorMessage.length() == 0){
                    errorMessage = validateAge(ageStr);
                }
                if(errorMessage.length() == 0){
                    errorMessage = validateCap(capacityStr);
                }
                if(errorMessage.length() == 0){
                    String user = "";
                    try {
                        user = currentFirebaseUser.getUid();
                    } catch (Exception e){
                        user = "";
                    }
                    errorMessage = validateUser(user);
                }
                // Assume description can be empty
                if(errorMessage.length() == 0) {
                    Date day = new Date(dateStr);
                    int capacityDone = Integer.parseInt(capacityStr);
                    double costDone = Double.parseDouble(costStr);
                    int ageDone = Integer.parseInt(ageStr);
                    String currentUser = currentFirebaseUser.getUid();
                    Event newEvent = new Event(nameStr, addressStr, capacityDone, day.toString(), descriptionStr, costDone, ageDone, currentUser, 0, t);
                    addEventToDatabase(newEvent);
                    switchIntent(HomeActivity.class);
                } else {
                    information.setText(errorMessage);
                    information.setTextColor(Color.RED);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_event, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

//        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
//            switchIntent(ChangePasswordActivity.class); // Change this to the landscape version
//        }

        // Initialize auth and database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentFirebaseUser = mAuth.getCurrentUser();

        Button btnAdd = (Button) v.findViewById(R.id.backButton);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.doneButton);
        btnAdd.setOnClickListener(this);

        information = (TextView) v.findViewById(R.id.addInfo);

        name = (EditText) v.findViewById(R.id.eventName);
        description = (EditText) v.findViewById(R.id.description);
        cost = (EditText) v.findViewById(R.id.cost);
        age = (EditText) v.findViewById(R.id.age);
        date = (EditText) v.findViewById(R.id.date);
        capacity = (EditText) v.findViewById(R.id.capacity);
        address = (EditText) v.findViewById(R.id.address);
        time = (TimePicker) v.findViewById(R.id.timePicker);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
