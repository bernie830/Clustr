package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    TextView information;


    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    // TODO - Does nothing until the DB is implemented
    private void addEventToDatabase(String name, String des, String cost, String age, String date, String capacity, String address){

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
        if(!day.confirmDate()){
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

    private static String validateAddress(String address){
        String retVal = "";
        int len = address.length();
        boolean b = (len == 0);
        if(b) { retVal = "ERROR: An address must be entered for the event"; }
        return retVal;    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
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
                // Assume description can be empty
                if(errorMessage.length() == 0) {
                    addEventToDatabase(nameStr, descriptionStr, costStr, ageStr, dateStr, capacityStr, addressStr);
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

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(ChangePasswordActivity.class); // Change this to the landscape version
        }

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
