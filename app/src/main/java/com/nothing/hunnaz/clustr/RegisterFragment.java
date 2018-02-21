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

import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;


/**
 *
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private EditText passwordConfirmTextEntry;
    private EditText dobTextEntry;
    private Intent nextScreen;

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

    private static boolean confirmUsername(String username, UserSingleton singleton){
        boolean returnVal = true;
        if(singleton.isRegistered(username)) {
            returnVal = false;
        }

        return returnVal;
    }

    private static boolean confirmPassword(String password, String confirmationPassword){
        boolean returnVal = true;
        if(!password.equals(confirmationPassword) || password.length() < 8) {
            returnVal = false;
        }
        return returnVal;
    }

    private static boolean confirmDOB(String date){
        boolean returnVal = true;
        int slash = date.indexOf("/");
        String month = "";
        String day = "";
        String year = "";
        if(slash > -1) {
            month = date.substring(0, slash);
            String rest = date.substring(slash + 1);
            slash = rest.indexOf("/");
            if(slash > -1) {
                day = rest.substring(0, slash);
                year = rest.substring(slash + 1);
            } else {
                returnVal = false;
            }
        } else {
            returnVal = false;
        }

        if(returnVal){
            Date dob = new Date(month, day, year);
            returnVal = dob.confirmDate();
        }

        return returnVal;
    }

    private String registerUser(){
        String username = usernameTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String passwordConfirm = passwordConfirmTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();
        UserSingleton singleton = UserSingleton.get(this.getContext());

        String currentError = "";
        if(!confirmUsername(username, singleton)){
            currentError = "The username entered is not a valid username. Please enter another.";
        }

        if(currentError.length() == 0 && !confirmPassword(password, passwordConfirm)){
            currentError = "The password entered is either invalid or does not match the confirmation password. Please enter another.";
        }

        Date dob = new Date(date);

        if(currentError.length() == 0 && !dob.confirmDate()){
            currentError = "The date of birth entered is not a valid. Please enter another.";
        }

        if(currentError.length() == 0) {
            // Add the account to the db
            User account = new User(username, password, date);
            singleton.addAccount(account);

            UserPrefs.logInUser(username, this.getActivity());

            // Go onto the next screen
            startActivity(nextScreen);
        }
        return currentError;
    }

    private void returnToHome(){
        startActivity(nextScreen);
    }

    private void changeToLogin(){
        nextScreen = new Intent(this.getContext(), LoginActivity.class);
        startActivity(nextScreen);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        usernameTextEntry = (EditText) v.findViewById(R.id.userNameRegister);
        passwordTextEntry = (EditText) v.findViewById(R.id.passwordRegister);
        passwordConfirmTextEntry = (EditText) v.findViewById(R.id.passwordRegisterConfirm);
        dobTextEntry = (EditText) v.findViewById(R.id.dateOfBirthRegister);
        TextView errorMessageText = (TextView) v.findViewById(R.id.errorMessage);

        //Because from here we only ever want to return to the home screen
        nextScreen = new Intent(this.getContext(), HomeActivity.class);

        switch (view.getId()) {
            case R.id.backButtonRegister:
                returnToHome();
                break;
            case R.id.doneRegisterButton:
                String error = registerUser();
                if(error.length() > 0){
                    errorMessageText.setText(error);
                }
                break;
            case R.id.currentUserButton:
                changeToLogin();
                break;
        }
    }
}
