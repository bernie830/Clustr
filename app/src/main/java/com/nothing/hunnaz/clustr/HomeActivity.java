package com.nothing.hunnaz.clustr;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class HomeActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        Fragment retVal = new HomeFragment();
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        return retVal;
    }
}
