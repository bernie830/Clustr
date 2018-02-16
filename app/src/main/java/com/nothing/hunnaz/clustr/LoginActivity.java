package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private Intent nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Return an error message if there is one
    private String attemptLogin(){
        boolean validLogin = true;
        String retVal = "";
        if(validLogin) {
            startActivity(nextScreen);
        } else {
            retVal = "There was an error in login";
        }

        return retVal;
    }

    private void changeToRegister(){
        nextScreen = new Intent(this, RegisterActivity.class);
        startActivity(nextScreen);
    }

    private void displayUser(){
        UserSingleton singleton = UserSingleton.get(this.getApplicationContext());
        List<User> li = singleton.getAccounts();
    }

    private void returnToHome(){
        startActivity(nextScreen);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        usernameTextEntry = (EditText) v.findViewById(R.id.userName);
        passwordTextEntry = (EditText) v.findViewById(R.id.password);

        //Because from here we only ever want to return to the home screen
        nextScreen = new Intent(this, HomeActivity.class);

        switch (view.getId()) {
            case R.id.doneButton:
                attemptLogin();
                break;
            case R.id.registerButton:
                changeToRegister();
                break;
            case R.id.backButton:
                returnToHome();
                break;
        }
    }
}
