package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private EditText dobTextEntry;
    private Intent nextScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    private void addUser(){
        String username = usernameTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();

        UserSingleton singleton = UserSingleton.get(this.getApplicationContext());
        User account = new User(username, password, date);
        singleton.addAccount(account);
        startActivity(nextScreen);
    }
}
