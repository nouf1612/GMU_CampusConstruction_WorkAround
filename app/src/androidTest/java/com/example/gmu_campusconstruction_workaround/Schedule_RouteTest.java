package com.example.gmu_campusconstruction_workaround;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
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
public class Schedule_RouteTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void schedule_RouteTest() {

        /*  Test: Starts Activity
         *  - Enters schedule route activity and clears data for future tests
         */
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.button_GoToSR), withContentDescription("Schedule Route"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.coordinatorlayout.widget.CoordinatorLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_ClearDay), withText("Clear Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton1 = onView(withText("No schedule in Sunday.")).check(matches(isDisplayed()));
        appCompatButton1.perform(pressBack());

        /*  Test: Enter a start and end destination, and day into the spinners
         *  - Add Art and Design Building to Krug Hall and Sunday then check if its in list
         */
        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner_Start_Building2),
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
                .atPosition(1); //AandDBuilding
        appCompatTextView.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner_Dest_Building2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2); //Krug Hall
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.button_AddDay), withText("Add Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView = onView(withText("Buildings: Art and Design Building, Krug Hall\n\nRoute: Head east 230ft, Turn left at Enterprise Hall 530ft, Turn left after Planetary Hall towards the Johnson Center 50ft, Turn right towards New Academic Building 320ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn left 30ft.\n\n\n\n"));
        textView.check(matches(isDisplayed()));
        textView.perform(pressBack());

        /*  Test: Enter a start and end destination, and day into the spinners
         *  - Add Krug Hall to Art and Design Building and Sunday then check if its in list
         */
        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.spinner_Start_Building2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner3.perform(longClick());

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.spinner_Start_Building2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner4.perform(click());

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(8); //Krug Hall
        appCompatTextView3.perform(click());

        ViewInteraction appCompatSpinner5 = onView(
                allOf(withId(R.id.spinner_Dest_Building2),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner5.perform(click());

        DataInteraction appCompatTextView4 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2); //Art and Design Building
        appCompatTextView4.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.button_AddDay), withText("Add Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction textView2 = onView(withText("Buildings: Art and Design Building, Krug Hall\n\nRoute: Head east 230ft, Turn left at Enterprise Hall 530ft, Turn left after Planetary Hall towards the Johnson Center 50ft, Turn right towards New Academic Building 320ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn left 30ft.\n\n\n\nBuildings: Krug Hall, Art and Design Building\n\nRoute: From the east exit, Head towards the MIX building 30ft, Turn right and use the construction detour infront of Fenwick 0.2 miles, Turn left 320ft, Turn left towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.\n\n\n\n"));
        textView2.check(matches(isDisplayed()));
        textView2.perform(pressBack());

        /*  Test: Delete a start and end destination, and day into the spinners
         *  - Delete Krug Hall to Art and Design Building and Sunday then check if its in list
         */
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.button_DeleteDay), withText("Delete Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction textView3 = onView(withText("Buildings: Art and Design Building, Krug Hall\n\nRoute: Head east 230ft, Turn left at Enterprise Hall 530ft, Turn left after Planetary Hall towards the Johnson Center 50ft, Turn right towards New Academic Building 320ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn left 30ft.\n\n\n\n"));
        textView3.check(matches(isDisplayed()));
        textView3.perform(pressBack());

        /*  Test: Add a start and end destination, and day into the spinners
         *  - Add Krug Hall to Art and Design Building and Sunday then check if its in list
         */
        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.button_AddDay), withText("Add Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction textView4 = onView(withText("Buildings: Art and Design Building, Krug Hall\n\nRoute: Head east 230ft, Turn left at Enterprise Hall 530ft, Turn left after Planetary Hall towards the Johnson Center 50ft, Turn right towards New Academic Building 320ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn left 30ft.\n\n\n\nBuildings: Krug Hall, Art and Design Building\n\nRoute: From the east exit, Head towards the MIX building 30ft, Turn right and use the construction detour infront of Fenwick 0.2 miles, Turn left 320ft, Turn left towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.\n\n\n\n"));
        textView4.check(matches(isDisplayed()));
        textView4.perform(pressBack());

        /*  Test: Change Day. Add a start and end destination, and day into the spinners
         *  - Add Krug Hall to Art and Design Building and Wednesday then check if its in list
         */
        ViewInteraction appCompatSpinner6 = onView(
                allOf(withId(R.id.spinner_Days),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner6.perform(click());

        DataInteraction appCompatTextView5 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3); //Wednesday
        appCompatTextView5.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.button_AddDay), withText("Add Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction textView5 = onView(withText("Buildings: Krug Hall, Art and Design Building\n\nRoute: From the east exit, Head towards the MIX building 30ft, Turn right and use the construction detour infront of Fenwick 0.2 miles, Turn left 320ft, Turn left towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.\n\n\n\n"));
        textView5.check(matches(isDisplayed()));
        textView5.perform(pressBack());

        /*  Test: Clear Day
         *  - Clear schedules on Wednesday
         */
        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.button_ClearDay), withText("Clear Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatButton111 = onView(withText("Wednesday has been cleared.")).check(matches(isDisplayed()));
        appCompatButton111.perform(pressBack());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatButton33 = onView(withText("No route on Wednesday")).check(matches(isDisplayed()));
        appCompatButton33.perform(pressBack());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.button_DeleteDay), withText("Delete Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton15.perform(click());

        ViewInteraction appCompatButton44 = onView(withText("No route on Wednesday")).check(matches(isDisplayed()));
        appCompatButton44.perform(pressBack());

        /*  Test: Change Day. Add a start and end destination, and day into the spinners
         *  - Add Krug Hall to Art and Design Building and Tuesday then check if its in list
         */
        ViewInteraction appCompatSpinner7 = onView(
                allOf(withId(R.id.spinner_Days),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner7.perform(longClick());

        ViewInteraction appCompatSpinner8 = onView(
                allOf(withId(R.id.spinner_Days),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner8.perform(longClick());

        ViewInteraction appCompatSpinner9 = onView(
                allOf(withId(R.id.spinner_Days),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner9.perform(click());

        DataInteraction appCompatTextView6 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatTextView6.perform(click());

        ViewInteraction appCompatButton16 = onView(
                allOf(withId(R.id.button_AddDay), withText("Add Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton16.perform(click());

        ViewInteraction appCompatButton17 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton17.perform(click());

        ViewInteraction textView6 = onView(withText("Buildings: Krug Hall, Art and Design Building\n\nRoute: From the east exit, Head towards the MIX building 30ft, Turn right and use the construction detour infront of Fenwick 0.2 miles, Turn left 320ft, Turn left towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.\n\n\n\n"));
        textView6.check(matches(isDisplayed()));
        textView6.perform(pressBack());

        /*  Test: Change Day. Clear Day
         *  - Clear all routes on Sunday.
         */
        ViewInteraction appCompatSpinner10 = onView(
                allOf(withId(R.id.spinner_Days),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner10.perform(click());

        DataInteraction appCompatTextView7 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatTextView7.perform(click());

        ViewInteraction appCompatButton18 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton18.perform(click());

        ViewInteraction textView7 = onView(withText("Buildings: Art and Design Building, Krug Hall\n\nRoute: Head east 230ft, Turn left at Enterprise Hall 530ft, Turn left after Planetary Hall towards the Johnson Center 50ft, Turn right towards New Academic Building 320ft, Turn right and use the detour infront of Fenwick Library 0.2 miles, Turn left 30ft.\n\n\n\nBuildings: Krug Hall, Art and Design Building\n\nRoute: From the east exit, Head towards the MIX building 30ft, Turn right and use the construction detour infront of Fenwick 0.2 miles, Turn left 320ft, Turn left towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.\n\n\n\n"));
        textView7.check(matches(isDisplayed()));
        textView7.perform(pressBack());

        ViewInteraction appCompatButton19 = onView(
                allOf(withId(R.id.button_ClearDay), withText("Clear Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton19.perform(click());

        ViewInteraction appCompatButton122 = onView(withText("Sunday has been cleared.")).check(matches(isDisplayed()));
        appCompatButton122.perform(pressBack());

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton20.perform(click());

        ViewInteraction appCompatButton133 = onView(withText("No route on Sunday")).check(matches(isDisplayed()));
        appCompatButton133.perform(pressBack());

        /*  Test: Change Day. Clear Day
         *  - Clear all routes on Tuesday
         */
        ViewInteraction appCompatSpinner11 = onView(
                allOf(withId(R.id.spinner_Days),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatSpinner11.perform(click());

        DataInteraction appCompatTextView8 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(2);
        appCompatTextView8.perform(click());

        ViewInteraction appCompatButton21 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton21.perform(click());

        ViewInteraction textView8 = onView(withText("Buildings: Krug Hall, Art and Design Building\n\nRoute: From the east exit, Head towards the MIX building 30ft, Turn right and use the construction detour infront of Fenwick 0.2 miles, Turn left 320ft, Turn left towards David King Hall 50ft, Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.\n\n\n\n"));
        textView8.check(matches(isDisplayed()));
        textView8.perform(pressBack());

        ViewInteraction appCompatButton22 = onView(
                allOf(withId(R.id.button_DeleteDay), withText("Delete Route"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton22.perform(click());

        ViewInteraction appCompatButton23 = onView(
                allOf(withId(R.id.button_ViewDay), withText("View Day"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton23.perform(click());

        ViewInteraction appCompatButton144 = onView(withText("No route on Tuesday")).check(matches(isDisplayed()));
        appCompatButton144.perform(pressBack());

        ViewInteraction appCompatButton24 = onView(
                allOf(withId(R.id.button_Back), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton24.perform(click());
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
