package org.hogent.phonelibrary


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
/**
 * Adds devices to favorites and checks if they's sorted correct.
 *
 */
class SortingFavoritesTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun sortingFavoritesTest() {
        val appCompatEditText = onView(
            allOf(
                withId(R.id.inputText),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.inputText),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("xia"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.search_brand_button), withText("Search by brand"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.device_row),
                childAtPosition(
                    allOf(
                        withId(R.id.devicesRecyclerView),
                        childAtPosition(
                            withId(R.id.fastScroller),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.favoriteFloatingActionButton),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_search), withContentDescription("Search"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.inputText), withText("xia"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText("apple"))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.inputText), withText("apple"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.search_brand_button), withText("Search by brand"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val linearLayout2 = onView(
            allOf(
                withId(R.id.device_row),
                childAtPosition(
                    allOf(
                        withId(R.id.devicesRecyclerView),
                        childAtPosition(
                            withId(R.id.fastScroller),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout2.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.favoriteFloatingActionButton),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val bottomNavigationItemView2 = onView(
            allOf(
                withId(R.id.navigation_favorites), withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(300)

        val appCompatSpinner = onView(
            allOf(
                withId(R.id.sortChoice),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatSpinner.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(250)

        val appCompatCheckedTextView = onData(anything())
            .inAdapterView(
                childAtPosition(
                    withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                    0
                )
            )
            .atPosition(0)
        appCompatCheckedTextView.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.deviceNameTextView), withText("Black Shark"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Black Shark")))

        val textView2 = onView(
            allOf(
                withId(R.id.deviceNameTextView), withText("Watch 38mm"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Watch 38mm")))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(300)

        val appCompatSpinner2 = onView(
            allOf(
                withId(R.id.sortChoice),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment_container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatSpinner2.perform(click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(250)

        val appCompatCheckedTextView2 = onData(anything())
            .inAdapterView(
                childAtPosition(
                    withClassName(`is`("android.widget.PopupWindow\$PopupBackgroundView")),
                    0
                )
            )
            .atPosition(1)
        appCompatCheckedTextView2.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.deviceNameTextView), withText("Watch 38mm"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Watch 38mm")))

        val textView4 = onView(
            allOf(
                withId(R.id.deviceNameTextView), withText("Black Shark"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Black Shark")))

        val linearLayout3 = onView(
            allOf(
                withId(R.id.device_row),
                childAtPosition(
                    allOf(
                        withId(R.id.favoritesRecyclerView),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout3.perform(click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.favoriteFloatingActionButton),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val appCompatImageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageButton.perform(click())

        val textView5 = onView(
            allOf(
                withId(R.id.deviceNameTextView), withText("Black Shark"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Black Shark")))

        val linearLayout4 = onView(
            allOf(
                withId(R.id.device_row),
                childAtPosition(
                    allOf(
                        withId(R.id.favoritesRecyclerView),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout4.perform(click())

        val floatingActionButton4 = onView(
            allOf(
                withId(R.id.favoriteFloatingActionButton),
                childAtPosition(
                    allOf(
                        withId(R.id.constraintLayout),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton4.perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.navigation_favorites), withContentDescription("Favorites"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.nav_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
