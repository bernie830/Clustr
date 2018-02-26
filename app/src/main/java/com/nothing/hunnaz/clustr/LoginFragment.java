package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.nothing.hunnaz.clustr.UserDB.UserSingleton;


/**
 *
 */
public class LoginFragment extends Fragment implements View.OnClickListener{
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private TextView informationText;

    // Return an error message if there is one
    private String attemptLogin(String username, String password){
        UserSingleton singleton = UserSingleton.get(this.getContext());
        boolean validLogin = singleton.isValidLogin(username, password);
        String retVal = "";
        if(validLogin) {
            UserPrefs.logInUser(username, this.getContext());
            switchIntent(ListActivity.class);
        } else {
            retVal = "The entered information was invalid. Please try again.";
        }
        return retVal;
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    @Override
    public void onPause() {
        Log.d(getClass().getSimpleName(), "Entering onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(getClass().getSimpleName(), "Entering onResume");
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        usernameTextEntry = (EditText) v.findViewById(R.id.userName);
        passwordTextEntry = (EditText) v.findViewById(R.id.password);
        informationText = (TextView) v.findViewById(R.id.loginInfo);

        switch (view.getId()) {
            case R.id.doneButton:
                String newInfo = attemptLogin(usernameTextEntry.getText().toString(), passwordTextEntry.getText().toString());
                if(newInfo.length() != 0) {
                    informationText.setText(newInfo);
                }
                break;
            case R.id.registerButton:
                switchIntent(RegisterActivity.class);
                break;
            case R.id.backButton:
                switchIntent(HomeActivity.class);
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(LoginActivity.class); // Change this to the landscape version
        }

        Button btnAdd = (Button) v.findViewById(R.id.backButton);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.doneButton);
        btnAdd.setOnClickListener(this);

        btnAdd = (Button) v.findViewById(R.id.registerButton);
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
