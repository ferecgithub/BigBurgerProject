package com.ferechamitbeyli.bigburgerkotlin.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressMenuKey
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.ferechamitbeyli.bigburgerkotlin.CustomMatchers
import com.ferechamitbeyli.bigburgerkotlin.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

//    private val context: Context by lazy { activityRule.activity }

    @Test
    fun countProducts() {
        onView(withId(R.id.productsList)).inRoot(withDecorView(CustomMatchers.withItemCount(13)))

    }
    @Test
    fun progressBar_onScreen_exist() {
    onView(withId(R.id.loading_view)).inRoot(withDecorView(isDisplayed()))
}

    @Test
    fun cartIcon_onScreen_exist() {
        onView(withId(R.id.cart_menu)).check(matches(isDisplayed()))
    }

    @Test
    fun productListRecyclerView_onScreen_exist() {
        onView(withId(R.id.productsList)).inRoot(withDecorView(isDisplayed()))
    }

    @Test
    fun actionMenuButtons_onScreen_exist() {
        pressMenuKey()
        onView(withId(R.id.share)).inRoot(withDecorView(isDisplayed()))
        onView(withId(R.id.exit)).inRoot(withDecorView(isDisplayed()))
    }

    @Test
    fun error_onScreen_notExist() {
        onView(withId(R.id.list_error)).inRoot(withDecorView(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun productList_isBelowOf_searchBar() {
        onView(withId(R.id.productsList)).inRoot(withDecorView(hasChildCount(1)))
    }

    @Test
    fun theBigBurger_onScreen_exist() {
        onView(withText("The Big Burger")).inRoot(withDecorView(isDisplayed()))
    }

    @Test
    fun theBigCake_onScreen_notExist() {
        onView(withText("The Big Cake")).check(doesNotExist())
    }



}