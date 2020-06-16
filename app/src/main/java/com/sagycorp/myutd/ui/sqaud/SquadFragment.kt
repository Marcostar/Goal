package com.sagycorp.myutd.ui.sqaud

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sagycorp.myutd.R
import com.sagycorp.myutd.adapters.FavTeamRecyclerViewAdapter
import com.sagycorp.myutd.adapters.PlayerClick
import com.sagycorp.myutd.adapters.SqaudRecyclerViewAdapter
import com.sagycorp.myutd.adapters.TeamRowClick
import com.sagycorp.myutd.databinding.FragmentFavteamBinding
import com.sagycorp.myutd.databinding.FragmentSquadBinding
import com.sagycorp.myutd.ui.searchteam.FavTeamFragmentDirections
import com.sagycorp.myutd.ui.searchteam.FavTeamViewModel
import com.sagycorp.myutd.utils.BaseFragment

class SquadFragment : BaseFragment() {


    private lateinit var binding: FragmentSquadBinding
    private val viewModel: SquadViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, SquadViewModel.Factory(activity.application))
            .get(SquadViewModel::class.java)
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_squad, container, false
        )

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideFullScreen()
        binding.squadViewModel = viewModel

        val adapter = SqaudRecyclerViewAdapter(PlayerClick {

        })

        viewModel.isTeamSet.observe(viewLifecycleOwner, Observer {

            viewModel.getPlayerList(it[0].idTeam)

        })

        viewModel.playerList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.player)
            }
        })

        binding.playerList.adapter = adapter
    }
}
