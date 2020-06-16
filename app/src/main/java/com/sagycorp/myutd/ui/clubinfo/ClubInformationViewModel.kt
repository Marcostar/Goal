package com.sagycorp.myutd.ui.clubinfo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagycorp.myutd.data.Teams
import com.sagycorp.myutd.repository.SportsRepository
import com.sagycorp.myutd.room.SportsTeamDatabase
import com.sagycorp.myutd.ui.onboarding.OnBoardingViewModel
import com.sagycorp.myutd.ui.searchteam.RestApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

enum class RestApiStatus { LOADING, ERROR, DONE }

class ClubInformationViewModel(app: Application) : ViewModel() {

    private val sportsRepository = SportsRepository(SportsTeamDatabase.getInstance(app))

    val isTeamSet = sportsRepository.favTeam

    private val _status = MutableLiveData<RestApiStatus>()

    val status: LiveData<RestApiStatus>
        get() = _status

    private val _clubInfo = MutableLiveData<Teams>()

    val clubInfo: LiveData<Teams>
        get() = _clubInfo


    private var viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getClubInfo(teamId: Long)
    {
        viewModelScope.launch {
            try {
                _status.value = RestApiStatus.LOADING
                _clubInfo.value = sportsRepository.getClubInfo(teamId)
                _status.value = RestApiStatus.DONE
            }catch (e: Exception)
            {
                _status.value = RestApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClubInformationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClubInformationViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}