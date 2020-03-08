package com.challenge.thescorecodingchallenge.ui.fragment

import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.challenge.theScore.R
import com.challenge.theScore.core.data.Player
import com.challenge.theScore.core.data.TeamsModel
import com.challenge.theScore.presentation.activities.MainActivity
import com.challenge.theScore.presentation.fragments.TeamDetailFragment
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TeamDetailFragmentTest {

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
        val teamsModel = TeamsModel("45","42","MockTeam",1, listOf(Player(1,"Player","One","T",1)))
        val teamDetailFragment = TeamDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("teamModel",teamsModel)
        teamDetailFragment.arguments = bundle
        mActivity?.supportFragmentManager?.beginTransaction()?.replace(container?.id!!, teamDetailFragment)
            ?.commitAllowingStateLoss()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        val viewPlayer = teamDetailFragment.view?.findViewById<RecyclerView>(R.id.rv_players_list)
        TestCase.assertNotNull(viewPlayer)
    }

    @After
    fun tearDown() {
        mActivity = null
    }

}