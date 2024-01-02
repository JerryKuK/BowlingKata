package com.example.bowlingkata

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun setEditTextClearSet() {
        onView(withId(R.id.et_set))
            .perform(typeText("10"), clearText())
            .check(matches(withText("")))
    }

    @Test
    fun setEditText0Set() {
        onView(withId(R.id.et_set))
            .perform(replaceText("0"))
            .check(matches(withText("1")))
    }

    @Test
    fun setEditText11Set() {
        onView(withId(R.id.et_set))
            .perform(typeText("11"))
            .check(matches(withText("10")))
    }

    @Test
    fun set10Set() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
    }

    @Test
    fun set10SetTotalScore() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
        setScore(10)
        setScore(9, 1)
        setScore(9, 0)
        setScore(10)
        setScore(9, 1)
        setScore(10)
        setScore(10)
        setScore(9, 1)
        setScore(9, 1)
        setScore(9, 1, 9)
        onView(withText("194")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun set10SetTotalScore1() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
        setScore(8, 2)
        setScore(9, 0)
        setScore(7, 0)
        setScore(10)
        setScore(8, 2)
        setScore(9,1)
        setScore(6,3)
        setScore(7, 3)
        setScore(8, 2)
        setScore(1, 4)
        onView(withText("133")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun set10SetTotalScore2() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
        setScore(10)
        setScore(4, 4)
        setScore(7, 3)
        setScore(8,0)
        setScore(8, 2)
        setScore(0, 3)
        setScore(10)
        setScore(7, 1)
        setScore(8, 0)
        setScore(0, 9)
        onView(withText("108")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun set10SetTotalScore3() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
        setScore(10)
        setScore(8, 0)
        setScore(5, 4)
        setScore(8,1)
        setScore(8, 2)
        setScore(9,0)
        setScore(3,3)
        setScore(8, 2)
        setScore(10)
        setScore(10, 3, 3)
        onView(withText("137")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun set10SetTotalScore4() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
        setScore(9,1)
        setScore(0, 7)
        setScore(1, 7)
        setScore(0,9)
        setScore(8, 0)
        setScore(5,0)
        setScore(0,3)
        setScore(6, 4)
        setScore(8, 0)
        setScore(0, 8)
        onView(withText("84")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    @Test
    fun set10SetTotalScore5() {
        onView(withId(R.id.et_set)).perform(typeText("10"))
        onView(withId(R.id.button_set)).perform(click())
        onView(withId(R.id.ll_content)).check(matches(ViewMatchers.hasChildCount(10)))
        setScore(3,0)
        setScore(3, 5)
        setScore(6, 3)
        setScore(10)
        setScore(10)
        setScore(8,0)
        setScore(10)
        setScore(8, 2)
        setScore(7, 1)
        setScore(8, 0)
        onView(withText("127")).perform(scrollTo()).check(matches(isDisplayed()))
    }

    private fun setScore(score1: Int, score2: Int? = null, score3: Int? = null) {
        onView(withId(R.id.et_score)).perform(replaceText("$score1"))
        onView(withId(R.id.button_score)).perform(click())
        if(score2 != null) {
            onView(withId(R.id.et_score)).perform(replaceText("$score2"))
            onView(withId(R.id.button_score)).perform(click())
        }
        if(score3 != null) {
            onView(withId(R.id.et_score)).perform(replaceText("$score3"))
            onView(withId(R.id.button_score)).perform(click())
        }
    }
}