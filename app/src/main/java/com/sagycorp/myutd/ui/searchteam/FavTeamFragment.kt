package com.sagycorp.myutd.ui.searchteam

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sagycorp.myutd.R
import com.sagycorp.myutd.adapters.FavTeamRecyclerViewAdapter
import com.sagycorp.myutd.adapters.TeamRowClick
import com.sagycorp.myutd.databinding.FragmentFavteamBinding
import com.sagycorp.myutd.ui.onboarding.OnBoardingViewModel
import com.sagycorp.myutd.utils.BaseFragment

/**
 * A fragment representing a list of Items.
 */
class FavTeamFragment : BaseFragment() {

    private lateinit var binding: FragmentFavteamBinding

    private val viewModel: FavTeamViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, FavTeamViewModel.Factory(activity.application))
            .get(FavTeamViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favteam, container, false
        )

        binding.lifecycleOwner = this

        hideFullScreen()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.favTeamViewModel = viewModel

        binding.searchBar.isSubmitButtonEnabled = true

        val adapter = FavTeamRecyclerViewAdapter(TeamRowClick {
            viewModel.insertFavTeam(it)
            findNavController().navigate(FavTeamFragmentDirections.actionFavTeamFragmentToNavigationHome())
        })

        binding.searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                binding.kick.visibility = View.GONE
                binding.noTeam.visibility = View.GONE
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                binding.kick.visibility = View.GONE
                viewModel.findYourTeam(query)
                return false
            }

        })



        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("FavTeam",it.teams.size.toString())
                if (it.teams.isEmpty())
                {
                    binding.noTeam.visibility = View.VISIBLE
                    adapter.submitList(it.teams)
                }
                else
                {
                    binding.noTeam.visibility = View.GONE
                    adapter.submitList(it.teams)
                }

            }
        })

        binding.teamList.adapter = adapter

    }
}