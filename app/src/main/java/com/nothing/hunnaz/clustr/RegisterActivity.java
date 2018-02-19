package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private EditText passwordConfirmTextEntry;
    private EditText dobTextEntry;
    private Intent nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        return returnVal;
    }

    private String registerUser(){
        String username = usernameTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String passwordConfirm = passwordConfirmTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();
        UserSingleton singleton = UserSingleton.get(this.getApplicationContext());

        String currentError = "";
        if(!confirmUsername(username, singleton)){
            currentError = "The username entered is not a valid username. Please enter another.";
        }

        if(currentError.length() == 0 && !confirmPassword(password, passwordConfirm)){
            currentError = "The password entered is either invalid or does not match the confirmation password. Please enter another.";
        }

        if(currentError.length() == 0 && !confirmDOB(date)){
            currentError = "The date of birth entered is not a valid. Please enter another.";
        }

        if(currentError.length() == 0) {
            // Add the account to the db
            User account = new User(username, password, date);
            singleton.addAccount(account);

            // Set the user to logged in
            SharedPreferences settings = getPreferences(0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isLoggedIn", true);
            editor.putString("username", username);
            editor.commit();

            // Go onto the next screen
            startActivity(nextScreen);
        }
        return currentError;
    }

    private void returnToHome(){
        startActivity(nextScreen);
    }

    private void changeToLogin(){
        nextScreen = new Intent(this, LoginActivity.class);
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
        nextScreen = new Intent(this, HomeActivity.class);

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
