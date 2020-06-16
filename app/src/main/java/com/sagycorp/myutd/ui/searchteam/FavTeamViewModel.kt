package com.sagycorp.myutd.ui.searchteam

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.data.Teams
import com.sagycorp.myutd.repository.SportsRepository
import com.sagycorp.myutd.room.SportsTeamDatabase
import com.sagycorp.myutd.ui.onboarding.OnBoardingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

enum class RestApiStatus { LOADING, ERROR, DONE }

class FavTeamViewModel (app: Application) : ViewModel() {

    private val sportsRepository = SportsRepository(SportsTeamDatabase.getInstance(app))



    private val _status = MutableLiveData<RestApiStatus>()

    val status: LiveData<RestApiStatus>
        get() = _status

    private val _teamList = MutableLiveData<Teams>()

    val teamList: LiveData<Teams>
        get() = _teamList


    private var viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun findYourTeam(teamName: String)
    {
        viewModelScope.launch {
            try {
                _status.value = RestApiStatus.LOADING
                _teamList.value = sportsRepository.findYourTeam(teamName)
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

    fun insertFavTeam(favTeam: FavTeam) {
        viewModelScope.launch {
            sportsRepository.insertFavTeam(favTeam)
        }

    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavTeamViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavTeamViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}