package com.sagycorp.myutd.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.data.Player
import com.sagycorp.myutd.databinding.SquadItemBinding

class SqaudRecyclerViewAdapter(private val clickListener: PlayerClick):
    ListAdapter<Player, SqaudRecyclerViewAdapter.ViewHolder>(PlayerListDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(private val binding: SquadItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Player, clickListener: PlayerClick) {
            binding.playerList = item
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SquadItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PlayerListDiffCallback : DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.strPlayer == newItem.strPlayer
    }


    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }


}

class PlayerClick(val row: (Player) -> Unit) {

    fun onClick(player: Player) = row(player)
}