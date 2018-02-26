package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.nothing.hunnaz.clustr.UserDB.User;
import com.nothing.hunnaz.clustr.UserDB.UserSingleton;

public class RegisterActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        Fragment retVal = new RegisterFragment();
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            retVal =  new RegisterFragment(); // Change this to the landscape version
        }
        return retVal;
    }
}
