package com.sagycorp.myutd.ui.clubinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sagycorp.myutd.R
import com.sagycorp.myutd.databinding.FragmentClubinformationBinding
import com.sagycorp.myutd.databinding.FragmentOnboardingBinding
import com.sagycorp.myutd.ui.onboarding.OnBoardingFragmentDirections
import com.sagycorp.myutd.ui.onboarding.OnBoardingViewModel
import com.sagycorp.myutd.ui.searchteam.FavTeamFragmentDirections
import com.sagycorp.myutd.utils.BaseFragment

class ClubInformationFragment : BaseFragment() {

    private val viewModel: ClubInformationViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, ClubInformationViewModel.Factory(activity.application))
            .get(ClubInformationViewModel::class.java)
    }

    private lateinit var binding: FragmentClubinformationBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_clubinformation, container, false
        )


        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hideFullScreen()
        binding.clubViewModel = viewModel
        viewModel.isTeamSet.observe(viewLifecycleOwner, Observer {

            viewModel.getClubInfo(it[0].idTeam)

        })

        viewModel.clubInfo.observe(viewLifecycleOwner, Observer {
            if (it.teams.isNotEmpty())
            {
                val team = it.teams[0]
                binding.name.text = team.strTeam
                binding.year.text = team.intFormedYear
                binding.capacity.text = team.intStadiumCapacity
                binding.country.text = team.strCountry
                binding.league.text = team.strLeague
                binding.stadium.text = team.strStadium
                binding.stadiumLocation.text = team.strStadiumLocation

                Glide.with(binding.clubBadge.context)
                    .load(team.strTeamBadge)
                    .dontTransform()
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.clubBadge)

            }

        })

        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_info_to_favTeamFragment)
        }
    }
}
