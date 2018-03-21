package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Button;
import android.widget.TextView;

import com.nothing.hunnaz.clustr.EventDB.Event;

import java.text.DecimalFormat;


/**
 *
 */
public class ItemFragment extends Fragment implements View.OnClickListener{


    private Event event;
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
        frag.setArguments(args);
        return frag;
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.exitFab:
                closeFragment();
                break;
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

        event = new Event(title, location, capacity, date, description, cost, age, creator, num);

        //ImageView image = (ImageView) v.findViewById(R.id.eventImage);
        //image.setImageResource(R.mipmap.missing_img_round);

        setUpInformation(v);

        FloatingActionButton exit = (FloatingActionButton) v.findViewById(R.id.exitFab);
        exit.setOnClickListener(this);

        return v;
    }
}

