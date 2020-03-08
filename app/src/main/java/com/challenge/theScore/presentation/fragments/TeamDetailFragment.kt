package com.challenge.theScore.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.theScore.R
import com.challenge.theScore.core.data.TeamsModel
import com.challenge.theScore.databinding.FragmentTeamDetailBinding
import com.challenge.theScore.framework.baseclasses.BaseFragment
import com.challenge.theScore.presentation.activities.MainActivity
import com.challenge.theScore.presentation.adapters.PlayerAdapter

class TeamDetailFragment : BaseFragment<FragmentTeamDetailBinding>() {

    lateinit var actionBar: ActionBar
    lateinit var teamModel: TeamsModel

    override fun getLayoutId(): Int = R.layout.fragment_team_detail

    override fun onAttach(context: Context) {
        super.onAttach(context)
        actionBar = (activity as MainActivity).supportActionBar!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        teamModel = arguments?.let { TeamDetailFragmentArgs.fromBundle(it).teamModel }!!
        fragmentBinding.teamModel = teamModel
        setActionBar()
        setUpUI()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Function to set up recycler view
     */
    private fun setUpUI() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentBinding.rvPlayersList.layoutManager = linearLayoutManager
        val playerAdapter = PlayerAdapter(teamModel.players)
        fragmentBinding.rvPlayersList.adapter = playerAdapter
    }

    /**
     * Function to set up action bar
     */
    private fun setActionBar() {
        actionBar.title = teamModel.fullName
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeButtonEnabled(true)
    }
}