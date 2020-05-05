package com.example.gmu_campusconstruction_workaround;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * UI Unit tester class for Quick Route
 *
 */
@RunWith(AndroidJUnit4.class)
public class Quick_Route_test {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * test the routes
     */
    @Test
    public void testRoutes(){

        //Test 1 check if the route given matches the route from Aquia to Art and design

        // go to quick route
        onView(withId(R.id.button_GoToQR)).perform(click());
        //click get route
        onView(withId(R.id.button_GetRoute)).perform(click());
        //test if the right route is showing
        onView(
                withText("Head south towards the Johnson Center 600ft, Turn left 400ft, " +
                        "Turn right after the Johnson Center 300ft, Turn left towards David King Hall 50ft, " +
                        "Turn right and use the construction detour infront of Planetary Hall 530ft, " +
                        "Turn right after Enterprise Hall 230ft.")).check(matches(isDisplayed()));
        pressBack();


        // Find the spinner and click on it.
        onView(withId(R.id.spinner_Start_Building)).perform(click());
        // Find the spinner item and click on it.
        onData(is("Robinson Hall B")).perform(click());

        onView(withId(R.id.spinner_Dest_Building)).perform(click());
        onData(is("Art and Design Building")).perform(click());
        //click get route
        onView(withId(R.id.button_GetRoute)).perform(click());
        //check route
        onView(withText("Head east 150ft, " +
                "Turn right after the Johnson Center 300ft, Turn left towards David King Hall 50ft, " +
                "Turn right and use the construction detour infront of Planetary Hall 530ft, Turn right after Enterprise Hall 230ft.")).check(matches(isDisplayed()));
        pressBack();

        onView(withId(R.id.button_GoToMA)).perform(click());
    }


    /**
     * Check if the start spinner is showing all of the buildings
     */
    @Test
    public void CheckSSpinner(){
        // go to quick route
        onView(withId(R.id.button_GoToQR)).perform(click());

        String[] startSpinner = mActivityTestRule.getActivity().getResources()
                .getStringArray(R.array.Start_Buildings);
        for (String s : startSpinner) {
            // Find the spinner and click on it.
            onView(withId(R.id.spinner_Start_Building)).perform(click());
            // Find the spinner item and click on it.
            onData(is(s)).perform(click());
        }

        onView(withId(R.id.button_GoToMA)).perform(click());
    }

    /**
     * Last test
     * check if the second spinner is changing according to the first
     * and if it gives the right results
     */
    @Test
    public void CheckDSpinner(){
        // go to quick route
        onView(withId(R.id.button_GoToQR)).perform(click());

        String[] startSpinner = mActivityTestRule.getActivity().getResources()
                .getStringArray(R.array.Start_Buildings);

        for (String s : startSpinner) {
            // Find the spinner and click on it.
            onView(withId(R.id.spinner_Start_Building)).perform(click());
            // Find the spinner item and click on it.
            onData(is(s)).perform(click());
            // write a method that finds the right list and sends back the length of the array
            SecondSpinner(s);

        }

       // go back to main activity
        onView(withId(R.id.button_GoToMA)).perform(click());
    }

    private void SecondSpinner(String s) {
        int dlength = listLength(s);
        String[] dest = getArray(s);
        for (int j = 0; j < dlength; j++){
            onView(withId(R.id.spinner_Dest_Building)).perform(click());
            onData(is(dest[j])).perform(click());
        }
    }

    private String[] getArray(String building) {

        String[] UP_Dest = mActivityTestRule.getActivity().getResources().getStringArray(R.array.UP_Dest);
        String[] JC_Dest = mActivityTestRule.getActivity().getResources().getStringArray(R.array.JC_Dest);
        String[] UP = mActivityTestRule.getActivity().getResources().getStringArray(R.array.Upper_Part);
        String[] darray = null;

        if(building.equals("Johnson Center")){
            darray = JC_Dest;
        }
        else{
            int listN = ListNum(building);
            if(listN == 1){
                darray = UP_Dest;
            }
            else if( listN == 2 || listN == 3){
                darray = UP;
            }
        }
        return darray;

    }

    /**
     * get length of the right array
     * @param building1 user choice
     * @return length
     */
    private int listLength(String building1){
        int length = 0;
        String[] UP_Dest = mActivityTestRule.getActivity().getResources().getStringArray(R.array.UP_Dest);
        String[] JC_Dest = mActivityTestRule.getActivity().getResources().getStringArray(R.array.JC_Dest);
        String[] UP = mActivityTestRule.getActivity().getResources().getStringArray(R.array.Upper_Part);

        if(building1.equals("Johnson Center")){
            length = JC_Dest.length;
        }
        else{
            int listN = ListNum(building1);
            if(listN == 1){
                length = UP_Dest.length;
            }
            else if( listN == 2 || listN == 3){
                length = UP.length;
            }
        }

        return length;
    }
    /**
     * figure out what list the users choice belongs in
     *
     * @param UserChoice check which list it belongs to
     * @return Lists number
     */
    private int ListNum(String UserChoice) {
        int list_num = 0;
        boolean cond = false;
        //Set arrays for comparison
        String[] UP = mActivityTestRule.getActivity().getResources().getStringArray(R.array.Upper_Part);
        String[] LP =  mActivityTestRule.getActivity().getResources().getStringArray(R.array.Lower_Part);
        String[] MP =  mActivityTestRule.getActivity().getResources().getStringArray(R.array.Middle_Part);
        // check which list the users choice belongs in
        //upper = 1, middle = 2, lower = 3
        for (int i = 1; i <= 3; i++) {
            if (i == 1) {
                cond = CheckList(UserChoice, UP);
            } else if (i == 2) {
                cond = CheckList(UserChoice, MP);
            } else {
                cond = CheckList(UserChoice, LP);
            }

            if (cond) {
                list_num = i;
                break;
            }
        }
        return list_num;
    }

    /**
     * Check if the users choice belongs to List
     *
     * @param UserChoice check if in List
     * @param List       check if the user choice is in it
     * @return true if the users choice is in List, false otherwise
     */
    private boolean CheckList(String UserChoice, String[] List) {
        boolean belongs_to_current_list = false;
        //see if user choice belongs to List
        for (String s : List) {
            if (UserChoice.equals(s)) {
                belongs_to_current_list = true;
                break;
            }
        }

        return belongs_to_current_list;
    }


}
