package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AccountActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    private void logoutUser(){
        UserPrefs.logOutUser(this);
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        ViewGroup v = (ViewGroup) view.getParent();

        switch (view.getId()) {
            case R.id.logoutButton:
                logoutUser();
                break;
        }
    }
}
