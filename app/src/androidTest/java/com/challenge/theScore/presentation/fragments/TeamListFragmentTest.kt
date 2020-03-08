package com.challenge.thescorecodingchallenge.ui.fragment

import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.challenge.theScore.R
import com.challenge.theScore.presentation.activities.MainActivity
import com.challenge.theScore.presentation.fragments.TeamListFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TeamListFragmentTest {

    @get:Rule
    var rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    var mActivity: MainActivity? = null
    @Before
    fun setUp() {
        mActivity = rule.activity
        rule.activity
            .supportFragmentManager.beginTransaction();
    }

    @Test
    fun testFragment()
    {
        val container = mActivity?.findViewById<FragmentContainerView>(R.id.fragment)
        TestCase.assertNotNull(container)

        val fragment = TeamListFragment()

        mActivity?.supportFragmentManager?.beginTransaction()?.replace(container?.id!!, fragment)
            ?.commitAllowingStateLoss()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        val view = fragment.view?.findViewById<RecyclerView>(R.id.rvTeamsList)
        TestCase.assertNotNull(view)


        Espresso.onView(ViewMatchers.withId(R.id.rvTeamsList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
    }

    @After
    fun tearDown() {
        mActivity = null
    }

}