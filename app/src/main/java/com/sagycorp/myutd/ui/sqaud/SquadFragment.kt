package com.sagycorp.myutd.ui.sqaud

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sagycorp.myutd.R
import com.sagycorp.myutd.adapters.PlayerClick
import com.sagycorp.myutd.adapters.SqaudRecyclerViewAdapter
import com.sagycorp.myutd.databinding.FragmentSquadBinding
import com.sagycorp.myutd.ui.BaseFragment

class SquadFragment : BaseFragment() {


    private lateinit var binding: FragmentSquadBinding
    private val viewModel: SquadViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, SquadViewModel.Factory(activity.application))
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
