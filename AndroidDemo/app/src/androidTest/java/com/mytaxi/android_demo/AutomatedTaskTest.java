package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class AutomatedTaskTest extends ActivityInstrumentationTestCase2<AuthenticationActivity> {

    public AutomatedTaskTest() {
        super(AuthenticationActivity.class);
    }

    void explicitWait() {
        SystemClock.sleep(2000);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
    }

    @Before
    public void grantPhonePermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + getTargetContext().getPackageName()
                            + " android.permission.ACCESS_FINE_LOCATION");
        }
        SystemClock.sleep(1000);
    }

    void loginUser() {
        onView(withId(R.id.edt_username)).perform(typeText("crazydog335"));
        onView(withId(R.id.edt_password)).perform(typeText("venture"));
        onView(withId(R.id.btn_login)).perform(click());
    }

    void searchDriver() {
        onView(withId(R.id.textSearch)).perform(typeText("sa"));
        onView(ViewMatchers.withText("Sarah Scott"))
                .inRoot(RootMatchers.isPlatformPopup()).check(matches(isDisplayed()));
    }

    void clickDriverName() {
        onView(ViewMatchers.withText("Sarah Scott"))
                .inRoot(RootMatchers.isPlatformPopup())
                .perform(ViewActions.click());
        onView(withId(R.id.imageViewDriverAvatar)).check(matches(isDisplayed()));
    }

    void tryCall() {
        onView(withId(R.id.fab)).perform(click());

    }

    @Test
    public void testAutomated() throws Exception {
        grantPhonePermission();
        loginUser();
        explicitWait();
        searchDriver();
        explicitWait();
        clickDriverName();
        tryCall();
    }
}