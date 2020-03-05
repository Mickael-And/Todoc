package com.cleanup.todoc;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.utils.TestUtils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.utils.TestUtils.atPosition;
import static com.cleanup.todoc.utils.TestUtils.clickChildView;
import static com.cleanup.todoc.utils.TestUtils.waitFor;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void filterAlertDialogIsLaunching() {
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withText(R.string.add_task)).check(matches(isDisplayed()));
    }

    @Test
    public void addAndRemoveTask() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        int sizeList = listTasks.getAdapter().getItemCount();

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTask is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one element only
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(sizeList + 1));

        onView(withId(R.id.list_tasks)).perform(actionOnItemAtPosition(sizeList, clickChildView(R.id.img_delete)));
        onView(isRoot()).perform(waitFor(1000));
        if (sizeList != 0) {
            assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
            assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
            onView(withId(R.id.list_tasks)).check(new TestUtils.ItemCount(sizeList));

        } else {
            assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
            assertThat(listTasks.getVisibility(), equalTo(View.GONE));
        }
    }

    @Test
    public void overflowMenuContainFourItems() {
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).check(matches(isDisplayed()));
        onView(withText(R.string.sort_alphabetical_invert)).check(matches(isDisplayed()));
        onView(withText(R.string.sort_oldest_first)).check(matches(isDisplayed()));
        onView(withText(R.string.sort_recent_first)).check(matches(isDisplayed()));
    }

    @Test
    public void sortTasks() {

        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        int sizeList = listTasks.getAdapter().getItemCount();

        // Filtre ancien -> récent de base
        onView(withId(R.id.list_tasks)).check((matches(atPosition(sizeList - 1, hasDescendant(withText("hhh Tâche example"))))));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withId(R.id.list_tasks)).check((matches(atPosition(0, hasDescendant(withText("aaa Tâche example"))))));
        onView(withId(R.id.list_tasks)).check((matches(atPosition(sizeList - 1, hasDescendant(withText("zzz Tâche example"))))));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withId(R.id.list_tasks)).check((matches(atPosition(0, hasDescendant(withText("zzz Tâche example"))))));
        onView(withId(R.id.list_tasks)).check((matches(atPosition(sizeList - 1, hasDescendant(withText("aaa Tâche example"))))));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withId(R.id.list_tasks)).check((matches(atPosition(sizeList - 1, hasDescendant(withText("hhh Tâche example"))))));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withId(R.id.list_tasks)).check((matches(atPosition(0, hasDescendant(withText("hhh Tâche example"))))));

        for (int i = 0; i < 3; i++) {
            onView(withId(R.id.list_tasks)).perform(actionOnItemAtPosition(0, clickChildView(R.id.img_delete)));
            onView(isRoot()).perform(waitFor(1000));
        }
    }
}
