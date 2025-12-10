package com.example.currencyrates;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUserCanTypeFilterText() {
        // Check EditText is visible
        onView(withId(R.id.edtFilter))
                .check(matches(isDisplayed()));

        // Type a filter text
        onView(withId(R.id.edtFilter))
                .perform(typeText("USD"), closeSoftKeyboard());

        // Verify the text was correctly typed
        onView(withId(R.id.edtFilter))
                .check(matches(withText("USD")));
    }

    @Test
    public void testListViewIsVisible() {
        // Check ListView is displayed
        onView(withId(R.id.listRates))
                .check(matches(isDisplayed()));
    }
}
