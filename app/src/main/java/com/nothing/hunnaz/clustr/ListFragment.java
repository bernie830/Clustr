package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;


/**
 *
 */
public class ListFragment extends Fragment implements View.OnClickListener{

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    private void showItem(){

        FragmentManager manager = getFragmentManager();
        ItemFragment itemFragment = new ItemFragment();
        FragmentTransaction transaction = manager.beginTransaction().add(this.getId(), itemFragment);
        transaction.addToBackStack("added");
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.accountButton:
                switchIntent(AccountActivity.class);
                break;
            case R.id.testShow:
                showItem();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(!UserPrefs.isLoggedIn(this.getContext())){
            switchIntent(LoginActivity.class);
        }
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(ListActivity.class); // Change this to the landscape version
        }

        Button btnAdd = (Button) v.findViewById(R.id.accountButton);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.testShow);
        btnAdd.setOnClickListener(this);


        // Not sure what this does but probably important
        if(false) {
            FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.addEventButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        return v;
    }
}
