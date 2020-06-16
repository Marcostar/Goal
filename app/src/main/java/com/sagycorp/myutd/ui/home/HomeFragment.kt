package com.sagycorp.myutd.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sagycorp.myutd.R
import com.sagycorp.myutd.databinding.FragmentHomeBinding
import com.sagycorp.myutd.databinding.FragmentOnboardingBinding
import com.sagycorp.myutd.ui.onboarding.OnBoardingViewModel
import com.sagycorp.myutd.utils.BaseFragment

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, HomeViewModel.Factory(activity.application))
            .get(HomeViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )

        binding.lifecycleOwner = this



        val nav: BottomNavigationView? = activity?.findViewById(R.id.nav_view)
        if (nav != null) {
            nav.visibility = View.VISIBLE
        }


        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        viewModel.webURL.observe(viewLifecycleOwner, Observer {

            binding.webView.loadUrl(viewModel.getURL(it[0].strWebsite))

        })

        hideFullScreen()

        return binding.root
    }
}
