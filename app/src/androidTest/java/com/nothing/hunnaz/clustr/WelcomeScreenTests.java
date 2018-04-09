package com.nothing.hunnaz.clustr;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class WelcomeScreenTests {

    private String mWelcomeMessage = "Welcome to Clustr";
    private String mLoginButton = "Login";


    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule = new ActivityTestRule<>(
            WelcomeActivity.class);

    @Test
    public void testWelcomeMessage(){
        onView(withId(R.id.welcomeMessage)).check(matches(withText(mWelcomeMessage)));
    }

    @Test
    public void testLoginButtonText(){
        onView(withId(R.id.loginButton)).check(matches(withText(mLoginButton)));
    }
}