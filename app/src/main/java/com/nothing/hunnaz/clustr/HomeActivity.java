package com.nothing.hunnaz.clustr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;

import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        isLoggedIn = UserPrefs.isLoggedIn(this);
        if(isLoggedIn){
            Button loginButton = (Button) this.findViewById(R.id.loginButton);
            String accountText = "Account Home";
            loginButton.setText(accountText);
        }
    }

    private void switchToLogin(){
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
    }

    private void switchToAccount(){
        Intent myIntent = new Intent(this, AccountActivity.class);
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
}
