package com.nothing.hunnaz.clustr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

        Button btnAdd = v.findViewById(R.id.cancelButtonRegister);
        btnAdd.setOnClickListener(this);

        btnAdd = v.findViewById(R.id.doneRegisterButton);
        btnAdd.setOnClickListener(this);

        usernameTextEntry = (EditText) v.findViewById(R.id.userNameRegister);
        passwordTextEntry = (EditText) v.findViewById(R.id.passwordRegister);
        passwordConfirmTextEntry = (EditText) v.findViewById(R.id.passwordRegisterConfirm);
        dobTextEntry =  (EditText) v.findViewById(R.id.dateOfBirthRegister);

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

    private static String confirmPasswordValidity(String password){
        // ADD Password validation such as numbers, letters, etc.
        String returnVal = "";
        int passLength = 5;

        if(password.length() < passLength) {
            returnVal = "ERROR: The password entered is not long enough. Must be at least " + passLength + " characters.";
        }
        return returnVal;
    }

    private String registerUser() {
        String username = usernameTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String passwordConfirm = passwordConfirmTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();
        UserSingleton singleton = UserSingleton.get(this.getContext());
        Date dob = new Date(date);
        String currentError = "";

       String passwordError = confirmPasswordValidity(password);

        if (username.equals("") || password.equals("") || passwordConfirm.equals("") || date.equals("")) {
            currentError = "ERROR: All fields are required to be filled in";
        } else if (!confirmUsername(username, singleton)) {
            currentError = "ERROR: The username entered is not a valid username.";
        } else if (passwordError.length() > 0) {
            currentError = passwordError;
        }else if(!confirmPasswordEquality(password, passwordConfirm)) {
            currentError = "ERROR: The entered password does not match the confirmation password.";
        } else if (!dob.confirmDate()) {
            currentError = "ERROR: The date of birth entered is not valid.";
        } else {
            //NO errors occured

            // Add the account to the db
            User account = new User(date, "email@email.com", password, username);
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
        ViewGroup v = (ViewGroup) view.getParent().getParent();
        TextView errorMessageText = (TextView) v.findViewById(R.id.errorMessage);

        //Because from here we only ever want to return to the Home screen
        //TODO Change to go to the home screen

        switch (view.getId()) {
            case R.id.cancelButtonRegister:
                switchIntent(LoginActivity.class);
                break;
            case R.id.doneRegisterButton:
                String error = registerUser();
                if(error.length() > 0){
                    errorMessageText.setText(error);
                    errorMessageText.setTextColor(Color.RED);
                } else {
                    // Logs the user in automatically if no errors
                    switchIntent(HomeActivity.class);
                }
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
