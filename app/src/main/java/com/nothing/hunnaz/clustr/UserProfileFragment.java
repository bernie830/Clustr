package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();


        // Not really lol
        Button btnAdd = (Button) v.findViewById(R.id.backButtonRegister);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.currentUserButton);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.doneRegisterButton);
        btnAdd.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        switch (view.getId()) {
            case R.id.backButtonRegister:
                break;
        }
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
