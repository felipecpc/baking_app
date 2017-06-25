package android.example.com.bakingapp;

import android.example.com.bakingapp.connection.ConnectionManager;
import android.example.com.bakingapp.connection.MyExecutor;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {

        // changing idling timeout
        IdlingPolicies.setMasterPolicyTimeout(
                60000 * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(
                60000 * 2, TimeUnit.MILLISECONDS);

        // registering IdlingResource with Espresso
        Espresso.registerIdlingResources(ConnectionManager.getMyExecutor());
        ConnectionManager.getMyExecutor().setIdleState(false);

        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        //Check ingredients
        ViewInteraction textView = onView(
                allOf(withId(R.id.textView_ingredients_title)));
        textView.check(matches(withText("Ingredients:")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView_ingredients_details),
                        isDisplayed()));
        textView2.check(matches(withText("2.0 CUP Graham Cracker crumbs\n6.0 TBLSP unsalted butter, melted\n0.5 CUP granulated sugar\n1.5 TSP salt\n5.0 TBLSP vanilla\n1.0 K Nutella or other chocolate-hazelnut spread\n500.0 G Mascapone Cheese(room temperature)\n1.0 CUP heavy cream(cold)\n4.0 OZ cream cheese(softened)\n")));

        //Click at first step
        onView(withId(R.id.rv_recipe_details))
                .perform(actionOnItemAtPosition(1, click()));

        onView(withText("Step 1")).check(matches(isDisplayed()));

        //Check navigation buttons

        onView(withId(R.id.button_previous_step))
                .perform(click());

        //Chekc that still same page
        onView(withText("Step 1")).check(matches(isDisplayed()));

        onView(withId(R.id.button_next_step))
                .perform(click());
        //Check that now it is in the step 2
        onView(withText("Step 2")).check(matches(isDisplayed()));


        //Go to step 1
        onView(withId(R.id.button_previous_step))
                .perform(click());
        onView(withText("Step 1")).check(matches(isDisplayed()));

        //Check it is on the step 1


        onView(withId(R.id.button_previous_step))
                .perform(click());


        //Check it continues on step 1
        onView(withText("Step 1")).check(matches(isDisplayed()));

        Espresso.unregisterIdlingResources(ConnectionManager.getMyExecutor());


    }

}
