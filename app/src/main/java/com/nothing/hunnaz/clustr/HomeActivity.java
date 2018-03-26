package com.nothing.hunnaz.clustr;

import android.support.v4.app.Fragment;
import android.view.Surface;

public class HomeActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        Fragment retVal = new HomeFragment();
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        return retVal;
    }
}
