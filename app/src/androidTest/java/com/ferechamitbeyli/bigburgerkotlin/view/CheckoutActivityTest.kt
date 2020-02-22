package com.ferechamitbeyli.bigburgerkotlin.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.ferechamitbeyli.bigburgerkotlin.R
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class CheckoutActivityTest {


    @Rule
    @JvmField
    var activityRule = ActivityTestRule(CheckoutActivity::class.java)

//    private val context: Context by lazy { activityRule.activity }

    @Test
    fun cartRecyclerView_onScreen_exist() {
        onView(withId(R.id.recycler_cart)).inRoot(withDecorView(isDisplayed()))
    }

    @Test
    fun placeOrderButton_onScreen_exist() {
        onView(withId(R.id.btn_place_order)).check(matches(isDisplayed()))
    }

    @Test
    fun backToMenuButton_onScreen_exist() {
        onView(withId(R.id.btn_back_to_menu)).check(matches(isDisplayed()))
    }

    @Test
    fun toastMessage_onScreen_exist() {
        onView(withId(android.R.id.message))
            .inRoot(withDecorView(not(`is`(activityRule.activity.window.decorView))))
        .check(matches(withText(R.string.empty_basket_text)))
    }

}