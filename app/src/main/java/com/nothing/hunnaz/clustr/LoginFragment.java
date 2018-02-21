package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
    private Intent nextScreen;

    // Return an error message if there is one
    private String attemptLogin(String username, String password){
        UserSingleton singleton = UserSingleton.get(this.getContext());
        boolean validLogin = singleton.isValidLogin(username, password);
        String retVal = "";
        if(validLogin) {
            UserPrefs.logInUser(username, this.getContext());
            startActivity(nextScreen);
        } else {
            retVal = "The entered information was invalid. Please try again.";
        }
        return retVal;
    }

    private void changeToRegister(){
        nextScreen = new Intent(this.getContext(), RegisterActivity.class);
        startActivity(nextScreen);
    }

    private void returnToHome(){
        startActivity(nextScreen);
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

        //Because from here we only ever want to return to the home screen
        nextScreen = new Intent(this.getContext(), HomeActivity.class);

        switch (view.getId()) {
            case R.id.doneButton:
                String newInfo = attemptLogin(usernameTextEntry.getText().toString(), passwordTextEntry.getText().toString());
                if(newInfo.length() != 0) {
                    informationText.setText(newInfo);
                }
                break;
            case R.id.registerButton:
                changeToRegister();
                break;
            case R.id.backButton:
                returnToHome();
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();


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
