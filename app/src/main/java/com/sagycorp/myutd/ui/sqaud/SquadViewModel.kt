package com.sagycorp.myutd.ui.sqaud

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagycorp.myutd.data.PlayerList
import com.sagycorp.myutd.data.Teams
import com.sagycorp.myutd.repository.SportsRepository
import com.sagycorp.myutd.room.SportsTeamDatabase
import com.sagycorp.myutd.ui.searchteam.FavTeamViewModel
import com.sagycorp.myutd.ui.searchteam.RestApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.lang.Exception

enum class RestApiStatus { LOADING, ERROR, DONE }

class SquadViewModel (app: Application): ViewModel() {

    private val _isTeamSet = SportsRepository(SportsTeamDatabase.getInstance(app))

    val isTeamSet = _isTeamSet.favTeam

    private val sportsRepository = SportsRepository(SportsTeamDatabase.getInstance(app))

    private val _status = MutableLiveData<RestApiStatus>()

    val status: LiveData<RestApiStatus>
        get() = _status

    private val _playerList = MutableLiveData<PlayerList>()

    val playerList: LiveData<PlayerList>
        get() = _playerList


    private var viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun getPlayerList(teamId: Long)
    {
        viewModelScope.launch {
            try {
                _status.value = RestApiStatus.LOADING
                _playerList.value = sportsRepository.getPlayerList(teamId)
                _status.value = RestApiStatus.DONE
            }catch (e: Exception)
            {
                Log.d("SquadViewModel","No Internet")
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
            if (modelClass.isAssignableFrom(SquadViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SquadViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}