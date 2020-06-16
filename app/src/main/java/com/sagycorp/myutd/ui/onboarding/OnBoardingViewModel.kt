package com.sagycorp.myutd.ui.onboarding

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.sagycorp.myutd.repository.SportsRepository
import com.sagycorp.myutd.room.SportsTeamDatabase

class OnBoardingViewModel(app: Application) : ViewModel() {

    private val _isTeamSet = SportsRepository(SportsTeamDatabase.getInstance(app))

    val isTeamSet = _isTeamSet.favTeam


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnBoardingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OnBoardingViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}