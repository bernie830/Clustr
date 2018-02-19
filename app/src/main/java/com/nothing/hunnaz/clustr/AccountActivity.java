package com.nothing.hunnaz.clustr;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class AccountActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new AccountFragment();
    }
}
