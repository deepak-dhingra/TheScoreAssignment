package com.challenge.theScore.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.theScore.core.data.TeamsModel
import com.challenge.theScore.databinding.LayoutTeamItemBinding

class TeamAdapter(var teamsList: ArrayList<TeamsModel>, var itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(item: TeamsModel)
    }

    fun updateData(list: List<TeamsModel>) {
        teamsList.clear()
        teamsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutTeamItemBinding.inflate(inflater, parent, false)
        return TeamViewHolder(binding)
    }

    override fun getItemCount(): Int = teamsList.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamsList[position])
    }

    inner class TeamViewHolder(var binding: LayoutTeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(teamModel: TeamsModel) {
            binding.teamModel = teamModel
            binding.executePendingBindings()
            binding.llItemClick.setOnClickListener(View.OnClickListener {
                itemClickListener.onItemClick(teamModel)
            })

        }

    }
}