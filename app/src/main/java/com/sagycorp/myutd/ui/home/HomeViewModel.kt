package com.sagycorp.myutd.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sagycorp.myutd.repository.SportsRepository
import com.sagycorp.myutd.room.SportsTeamDatabase
import com.sagycorp.myutd.ui.clubinfo.ClubInformationViewModel

class HomeViewModel(app: Application): ViewModel() {

    private val sportsRepository = SportsRepository(SportsTeamDatabase.getInstance(app))

    val webURL = sportsRepository.favTeam


    fun getURL(webUrl: String): String {
        var url = webUrl
        if(!url.startsWith("www.")&& !url.startsWith("http://")){
            url = "www.$url"
        }
        if(!url.startsWith("http://")){
            url = "https://$url"
        }
        return url
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}