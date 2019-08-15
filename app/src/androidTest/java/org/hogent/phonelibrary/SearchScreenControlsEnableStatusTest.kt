package org.hogent.phonelibrary


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
/**
 * The search buttons get disabled when the text is empty in the input field.
 *
 */
class SearchScreenControlsEnableStatusTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    // Removed child selector from the test because guidelines mess up the index.

    @Test
    fun searchScreenControlsEnableStatus() {
        val button = onView(
            allOf(
                withId(R.id.search_name_button),
                isDisplayed()
            )
        )
        button.check(matches(not(isEnabled())))

        val button2 = onView(
            allOf(
                withId(R.id.search_brand_button),
                isDisplayed()
            )
        )
        button2.check(matches(not(isEnabled())))

        val appCompatEditText = onView(
            allOf(
                withId(R.id.inputText),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.inputText),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("x"), closeSoftKeyboard())

        val button3 = onView(
            allOf(
                withId(R.id.search_name_button),
                isDisplayed()
            )
        )
        button3.check(matches(isEnabled()))

        val button4 = onView(
            allOf(
                withId(R.id.search_brand_button),
                isDisplayed()
            )
        )
        button4.check(matches(isEnabled()))

        val appCompatEditText3 = onView(
            allOf(
                withId(R.id.inputText), withText("x"),
                isDisplayed()
            )
        )
        appCompatEditText3.perform(replaceText(""))

        val appCompatEditText4 = onView(
            allOf(
                withId(R.id.inputText),
                isDisplayed()
            )
        )
        appCompatEditText4.perform(closeSoftKeyboard())

        val button5 = onView(
            allOf(
                withId(R.id.search_name_button),
                isDisplayed()
            )
        )
        button5.check(matches(not(isEnabled())))

        val button6 = onView(
            allOf(
                withId(R.id.search_brand_button),
                isDisplayed()
            )
        )
        button6.check(matches(not(isEnabled())))
    }
}
