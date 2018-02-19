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


/**
 *
 */
public class HomeFragment extends Fragment implements View.OnClickListener{


    private boolean isLoggedIn = false;

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
            Button loginButton = (Button) this.getActivity().findViewById(R.id.loginButton);
            String accountText = "Account Home";
            loginButton.setText(accountText);
        }

        Button btnAdd = (Button) v.findViewById(R.id.loginButton);
        btnAdd.setOnClickListener(this);

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
