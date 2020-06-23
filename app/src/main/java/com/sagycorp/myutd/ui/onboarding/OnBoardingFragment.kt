package com.sagycorp.myutd.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sagycorp.myutd.R
import com.sagycorp.myutd.databinding.FragmentOnboardingBinding
import com.sagycorp.myutd.ui.BaseFragment
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class OnBoardingFragment : BaseFragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    private lateinit var binding: FragmentOnboardingBinding

    private val viewModel: OnBoardingViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, OnBoardingViewModel.Factory(activity.application))
            .get(OnBoardingViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        showFullScreen()

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_onboarding, container, false
        )



        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launch {
            delay(3000)
            withContext(Dispatchers.Main){
                viewModel.isTeamSet.observe(viewLifecycleOwner, Observer {
                    if (it.isNotEmpty())
                    {
                        findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToNavigationHome())
                    }
                    else
                    {
                        findNavController().navigate(OnBoardingFragmentDirections.actionOnBoardingFragmentToFavTeamFragment())
                    }
                })
            }
        }
    }
}