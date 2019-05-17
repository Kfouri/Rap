package com.kfouri.rappitest.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kfouri.rappitest.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityVideoTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void popularFilterTest() throws InterruptedException {

        Thread.sleep(3000);

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.popularFilter),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatEditText.perform(scrollTo(), replaceText("game of"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image_poster),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.popularList),
                                        0),
                                0)));
        appCompatImageView.perform(scrollTo(), click());

        Thread.sleep(2000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtTitle), withText("Juego de Tronos"),
                        childAtPosition(
                                allOf(withId(R.id.generalConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.scrollView3),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Juego de Tronos")));
    }

    @Test
    public void topRatedFilterTest() throws InterruptedException {

        Thread.sleep(3000);

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.topRatedFilter),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                6)));
        appCompatEditText.perform(scrollTo(), replaceText("cach"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image_poster),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.topRatedList),
                                        0),
                                0)));
        appCompatImageView.perform(scrollTo(), click());

        Thread.sleep(2000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtTitle), withText("Cachitos de hierro y cromo"),
                        childAtPosition(
                                allOf(withId(R.id.generalConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.scrollView3),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Cachitos de hierro y cromo")));
    }

    @Test
    public void upcomingFilterTest() throws InterruptedException {

        Thread.sleep(3000);

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.upcomingFilter),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                7)));
        appCompatEditText.perform(scrollTo(), replaceText("pro"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image_poster),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.upcomingList),
                                        0),
                                0)));
        appCompatImageView.perform(scrollTo(), click());

        Thread.sleep(2000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.txtTitle), withText("Richard Says Goodbye"),
                        childAtPosition(
                                allOf(withId(R.id.generalConstraintLayout),
                                        childAtPosition(
                                                withId(R.id.scrollView2),
                                                0)),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Richard Says Goodbye")));
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
