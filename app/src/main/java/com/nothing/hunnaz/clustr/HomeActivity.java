package com.nothing.hunnaz.clustr;

import android.support.v4.app.Fragment;

/**
 * Created by Zane Clymer on 2/28/2018.
 */

public class HomeActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() { return new HomeFragment(); }

}
