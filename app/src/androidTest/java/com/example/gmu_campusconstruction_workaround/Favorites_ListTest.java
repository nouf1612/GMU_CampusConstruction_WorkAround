package com.example.gmu_campusconstruction_workaround;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Favorites_ListTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void favorites_ListTest() {

        /*  Test: Starts Activity
         *  - Enters favorites list activity and clears data for future tests
         */
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.button_GoToFL), withContentDescription("View Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.button_clear), withText("Clear All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton1 = onView(withText("Favorites is already empty")).check(matches(isDisplayed()));
        appCompatButton1.perform(pressBack());

        /*  Test: Enter a start & end destination into the spinners
         *  - Add JC to Engineering then check if its in list
         */
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_Start_Building),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(7); //Johnson Center
        appCompatTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_Dest_Building),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5); //Engineering Building
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_addFav), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_View), withText("View All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton3.perform(click());

        /* Checks if route was shown to user */
        ViewInteraction textView = onView(withText("Buildings: Johnson Center, Nguyen Engineering Building\n\nRoute: From the east exit, Head towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 0.2 miles.\n\n\n\n"));
        textView.check(matches(isDisplayed()));
        textView.perform(pressBack());

        /*  Test: Insert different route
         *  - Change Engineering to Lecture Hall then add to list
         */
        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinner_Dest_Building),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4); //Lecture Hall
        appCompatTextView3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_addFav), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button_View), withText("View All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView2 = onView(withText("Buildings: Johnson Center, Nguyen Engineering Building\n\nRoute: From the east exit, Head towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 0.2 miles.\n\n\n\nBuildings: Johnson Center, Lecture Hall\n\nRoute: From the north east exit, Head north 20ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn right 135ft, Turn left 80ft.\n\n\n\n"));
        textView2.check(matches(isDisplayed()));
        textView2.perform(pressBack());

        /*  Test: Removing data from the list
         *  - Deleting JC -> Engineering from list, leaving JC -> Lecture
         */
        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinner_Dest_Building),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        DataInteraction appCompatTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(5); //Engineering Building
        appCompatTextView4.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.button_deleteFav), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.button_View), withText("View All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction textView3 = onView(withText("Buildings: Johnson Center, Lecture Hall\n\nRoute: From the north east exit, Head north 20ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn right 135ft, Turn left 80ft.\n\n\n\n"));
        textView3.check(matches(isDisplayed()));
        textView3.perform(pressBack());

        /*  Test: Exits activity then re-enters
         *  - Goes back to main activity and shows data was saved
         */
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.button_GoToMA), withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.button_GoToFL), withContentDescription("View Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.button_View), withText("View All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction textView4 = onView(withText("Buildings: Johnson Center, Lecture Hall\n\nRoute: From the north east exit, Head north 20ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn right 135ft, Turn left 80ft.\n\n\n\n"));
        textView4.check(matches(isDisplayed()));
        textView4.perform(pressBack());

        /*  Test: Clears data and exits
         *  - Clears all data then quits back to main activity
         */
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.button_clear), withText("Clear All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction textView5 = onView(withText("Favorites has been cleared.")).check(matches(isDisplayed()));
        textView5.perform(pressBack());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.button_View), withText("View All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction textView6 = onView(withText("Favorites list is empty"));
        textView6.check(matches(isDisplayed()));
        textView6.perform(pressBack());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.button_GoToMA), withText("Previous"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton12.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
