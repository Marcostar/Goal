package com.sagycorp.myutd.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.sagycorp.myutd.R
import kotlinx.android.synthetic.main.fragment_clubinformation.*

open class BaseFragment: Fragment() {

    fun showFullScreen(){
        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val nav = activity?.findViewById<View>(R.id.nav_view)
        nav?.visibility = View.GONE
        activity?.actionBar?.hide()
    }

    fun hideFullScreen(){
        // Hide the status bar.
        activity?.window?.decorView?.systemUiVisibility = View.VISIBLE

        activity?.actionBar?.show()
    }

}