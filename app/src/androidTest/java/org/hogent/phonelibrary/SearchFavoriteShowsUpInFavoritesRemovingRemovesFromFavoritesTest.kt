package org.hogent.phonelibrary


import android.provider.Settings.Global.getString
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
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.platform.app.InstrumentationRegistry


@LargeTest
@RunWith(AndroidJUnit4::class)
/**
 * Adding and removing a device from/to favorites shows onto the favorites screen.
 *
 */
class SearchFavoriteShowsUpInFavoritesRemovingRemovesFromFavoritesTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchFavoriteShowsUpInFavorites_RemovingRemovesFromFavoritesTest() {
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
                withId(R.id.search_name_button), withText(R.string.search_by_name),
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
                    3
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.navigation_favorites), withContentDescription(R.string.bottom_nav_title_favorites),
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
        bottomNavigationItemView.perform(click())

        val textView = onView(
            allOf(
                withText("${getResourceString(R.string.title_activity_fragment_favorites)} (1)"),
                childAtPosition(
                    allOf(
                        withId(R.id.action_bar),
                        childAtPosition(
                            withId(R.id.action_bar_container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("${getResourceString(R.string.title_activity_fragment_favorites)} (1)")))

        val textView2 = onView(
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
        textView2.check(matches(withText("Black Shark")))

        val linearLayout2 = onView(
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

        val appCompatImageButton = onView(
            allOf(
                withContentDescription(R.string.action_bar_back_button_identifier),
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

        val textView3 = onView(
            allOf(
                withId(R.id.textViewNoFavorites), withText(R.string.no_favorite_devices_text),
                // Guidelines mess up index.
                isDisplayed()
            )
        )
        textView3.check(matches(withText(R.string.no_favorite_devices_text)))
    }

    /**
     * Helper function to get the string based on the string resource.
     *
     * @param id
     * @return
     */
    private fun getResourceString(id: Int): String {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.resources.getString(id)
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
