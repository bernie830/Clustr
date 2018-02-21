package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

public class RegisterActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new RegisterFragment();
    }
}
