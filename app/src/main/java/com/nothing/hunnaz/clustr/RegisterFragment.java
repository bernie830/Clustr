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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();

        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            switchIntent(RegisterActivity.class); // Change this to the landscape version
        }

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

    private static boolean confirmPasswordEquality(String password, String confirmationPassword){
        return password.equals(confirmationPassword);
    }

    private static boolean confirmPasswordValidity(String password){
        boolean returnVal = true;
        if(password.length() < 8) {
            returnVal = false;
        }
        return returnVal;
    }

    private static String confirmPassword(String password, String confirmationPassword){
        String retVal = "";
        if(!confirmPasswordValidity(password)){
            retVal = "The entered password is invalid. Please make sure the entered password is at least 8 characters in length";
        } else if(!confirmPasswordEquality(password, confirmationPassword)){
            retVal = "The entered password does not match the confirmation password. Please try again.";
        }
        return retVal;
    }

    private String registerUser(){
        String username = usernameTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String passwordConfirm = passwordConfirmTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();
        UserSingleton singleton = UserSingleton.get(this.getContext());

        String currentError = "";
        if(!confirmUsername(username, singleton)){
            currentError = "The username entered is already in use. Please enter another.";
        }

        if(currentError.length() == 0){
            currentError = confirmPassword(password, passwordConfirm);
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

            switchIntent(HomeActivity.class);
        }
        return currentError;
    }

    private void switchIntent(Class name){
        Intent myIntent = new Intent(this.getContext(), name);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        usernameTextEntry = (EditText) v.findViewById(R.id.userNameRegister);
        passwordTextEntry = (EditText) v.findViewById(R.id.passwordRegister);
        passwordConfirmTextEntry = (EditText) v.findViewById(R.id.passwordRegisterConfirm);
        dobTextEntry = (EditText) v.findViewById(R.id.dateOfBirthRegister);
        TextView errorMessageText = (TextView) v.findViewById(R.id.errorMessage);

        switch (view.getId()) {
            case R.id.backButtonRegister:
                switchIntent(WelcomeActivity.class);
                break;
            case R.id.doneRegisterButton:
                String error = registerUser();
                if(error.length() > 0){
                    errorMessageText.setText(error);
                }
                break;
            case R.id.currentUserButton:
                switchIntent(LoginActivity.class);
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
