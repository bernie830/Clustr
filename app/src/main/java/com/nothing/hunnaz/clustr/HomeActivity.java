package com.nothing.hunnaz.clustr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText usernameTextEntry;
    private EditText passwordTextEntry;
    private EditText dobTextEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    private void checkLogin(){
        String username = usernameTextEntry.getText().toString();
        String password = usernameTextEntry.getText().toString();
        String date = usernameTextEntry.getText().toString();
    }

    private void addUser(){
        String username = usernameTextEntry.getText().toString();
        String password = passwordTextEntry.getText().toString();
        String date = dobTextEntry.getText().toString();

        UserSingleton singleton = UserSingleton.get(this.getApplicationContext());
        User account = new User(username, password, date);
        singleton.addAccount(account);
    }

    private void displayUser(){
        UserSingleton singleton = UserSingleton.get(this.getApplicationContext());
        List<User> li = singleton.getAccounts();
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();
        usernameTextEntry = (EditText) v.findViewById(R.id.userName);
        passwordTextEntry = (EditText) v.findViewById(R.id.password);
        dobTextEntry = (EditText) v.findViewById(R.id.dateOfBirth);

        switch (view.getId()) {
            case R.id.doneButton:
                addUser();
                break;
            case R.id.checkButton:
                displayUser();
                break;
        }
    }
}
