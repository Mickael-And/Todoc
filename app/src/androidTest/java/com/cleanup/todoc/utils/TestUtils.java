package com.cleanup.todoc.utils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.cleanup.todoc.RecyclerViewMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by dannyroa on 5/9/15.
 */
public class TestUtils {


    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    }


    private static final class ScrollToPositionViewAction implements ViewAction {
        private final int position;

        private ScrollToPositionViewAction(int position) {
            this.position = position;
        }

        public Matcher<View> getConstraints() {
            return Matchers.allOf(new Matcher[]{
                    ViewMatchers.isAssignableFrom(RecyclerView.class), ViewMatchers.isDisplayed()
            });
        }

        public String getDescription() {
            return "scroll RecyclerView to position: " + this.position;
        }

        public void perform(UiController uiController, View view) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.scrollToPosition(this.position);
        }
    }

    /**
     * Fourni une {@link ViewAction} permettant de cliquer sur une vue à l'intérieur d'un item de la RecyclerView.
     *
     * @param id view que l'on souhaite
     * @return l'action
     */
    public static ViewAction clickChildView(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
    /**
     * Permet de réaliser un test sur un item d'une {@link RecyclerView}.
     *
     * @param position    position de l'item
     * @param itemMatcher le test
     * @return un matcher
     */
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    /**
     * Implementation d'un {@link ViewAssertion} permettant de tester la taille de la {@link RecyclerView}.
     */
    public static class ItemCount implements ViewAssertion {
        private final int expectedCount;

        public ItemCount(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

}