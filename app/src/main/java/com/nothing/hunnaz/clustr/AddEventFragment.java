package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 *
 */
public class AddEventFragment extends Fragment implements View.OnClickListener {
    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doneButton:
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_change_password, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(ChangePasswordActivity.class); // Change this to the landscape version
        }

        Button btnAdd = (Button) v.findViewById(R.id.backButton);
        btnAdd.setOnClickListener(this);

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
