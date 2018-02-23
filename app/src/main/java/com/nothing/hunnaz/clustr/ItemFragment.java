package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.Button;


/**
 *
 */
public class ItemFragment extends Fragment implements View.OnClickListener{


    private void closeFragment(){
        getFragmentManager().popBackStackImmediate();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();


        //ImageView image = (ImageView) v.findViewById(R.id.eventImage);
        //image.setImageResource(R.mipmap.missing_img_round);

        FloatingActionButton exit = (FloatingActionButton) v.findViewById(R.id.exitFab);
        exit.setOnClickListener(this);

        return v;
    }
}

