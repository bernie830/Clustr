package com.nothing.hunnaz.clustr;

import android.support.v4.app.Fragment;
import android.view.Surface;

public class LoginActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        Fragment retVal = new LoginFragment();
        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
            retVal = new LoginFragment(); // Change this to the landscape version
        }
        return retVal;
    }
}
