package com.challenge.theScore.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.theScore.R
import com.challenge.theScore.core.data.TeamsModel
import com.challenge.theScore.databinding.FragmentTeamListBinding
import com.challenge.theScore.framework.TeamListViewModel
import com.challenge.theScore.framework.baseclasses.BaseViewModelFragment
import com.challenge.theScore.presentation.activities.MainActivity
import com.challenge.theScore.presentation.adapters.TeamAdapter

class TeamListFragment : BaseViewModelFragment<FragmentTeamListBinding, TeamListViewModel>(),
    TeamAdapter.ItemClickListener {

    override fun getLayoutId(): Int = R.layout.fragment_team_list

    override fun getViewModel() = TeamListViewModel::class.java

    var teamList = ArrayList<TeamsModel>()

    lateinit var teamAdapter: TeamAdapter

    lateinit var actionBar: ActionBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        actionBar = (activity as MainActivity).supportActionBar!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return fragmentBinding.root
    }

    override fun onResume() {
        super.onResume()
        setActionBar()
        viewModel.getTeamList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUI()
        setUpSwipeRefresh()
        observeData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSortAlphabetical -> {
                viewModel.sortList("Names")
                true
            }
            R.id.menuSortWins -> {
                viewModel.sortList("wins")
                true
            }
            R.id.menuSortLosses -> {
                viewModel.sortList("losses")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(item: TeamsModel) {
        val action = TeamListFragmentDirections.actionToDetailsFragment(item)
        Navigation.findNavController(fragmentBinding.rvTeamsList).navigate(action)
    }

    /**
     * Function to set up Swipe Refresh View
     */
    private fun setUpSwipeRefresh() {
        fragmentBinding.swipeRefreshLayout.setOnRefreshListener {
            fragmentBinding.swipeRefreshLayout.isRefreshing = false
            viewModel.getTeamList()
        }
    }

    /**
     * Function to set up Action Bar
     */
    private fun setActionBar() {
        actionBar.title = "NBA Teams"
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setHomeButtonEnabled(false)
    }

    /**
     * Function to set up recycler view
     */
    private fun setUI() {
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentBinding.rvTeamsList.layoutManager = linearLayoutManager
        teamAdapter = TeamAdapter(teamList, this)
        fragmentBinding.rvTeamsList.adapter = teamAdapter
    }

    /**
     * Function to observe the data
     */
    private fun observeData() {
        viewModel.teamLoadError.observe(this, Observer {
            it.let {
                fragmentBinding.errorView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
        viewModel.teamsList.observe(this, Observer {
            it.let {
                fragmentBinding.rvTeamsList.visibility = View.VISIBLE
                teamAdapter.updateData(it)
            }
        })
        viewModel.loading.observe(this, Observer {
            it.let {
                fragmentBinding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })
    }
}