package org.hogent.phonelibrary


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFavoriteShowsIconInListTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchFavoriteShowsIconInList_RemovingRemovesIconFromList() {
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
                withId(R.id.search_name_button), withText("Search by name"),
                childAtPosition(
                    allOf(
                        withId(R.id.frameLayout3),
                        childAtPosition(
                            withId(R.id.fragment_container),
                            0
                        )
                    ),
                    5
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
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

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

        val imageView = onView(
            allOf(
                withId(R.id.favoriteIndicatorImageView),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

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
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val appCompatImageButton2 = onView(
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
        appCompatImageButton2.perform(click())

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
