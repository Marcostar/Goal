package com.sagycorp.myutd.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.databinding.TeamItemBinding


class FavTeamRecyclerViewAdapter(private val clickListener: TeamRowClick) :
    ListAdapter<FavTeam, FavTeamRecyclerViewAdapter.ViewHolder>(TeamListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(private val binding: TeamItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FavTeam, clickListener: TeamRowClick) {
            binding.teamList = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TeamItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


}

class TeamListDiffCallback : DiffUtil.ItemCallback<FavTeam>() {

    override fun areItemsTheSame(oldItem: FavTeam, newItem: FavTeam): Boolean {
        return oldItem.idTeam == newItem.idTeam
    }


    override fun areContentsTheSame(oldItem: FavTeam, newItem: FavTeam): Boolean {
        return oldItem == newItem
    }


}

class TeamRowClick(val row: (FavTeam) -> Unit) {

    fun onClick(favTeam: FavTeam) = row(favTeam)
}