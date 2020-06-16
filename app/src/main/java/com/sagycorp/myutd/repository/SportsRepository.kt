package com.sagycorp.myutd.repository

import SportsAPI
import androidx.lifecycle.LiveData
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.data.PlayerList
import com.sagycorp.myutd.data.Teams
import com.sagycorp.myutd.room.SportsTeamDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SportsRepository(private val database: SportsTeamDatabase ) {

    val favTeam: LiveData<List<FavTeam>> = database.teamDao.getYourTeam()

    suspend fun findYourTeam(teamName: String): Teams {
        return withContext(Dispatchers.IO) {
            val teams = SportsAPI.retrofitService.getFavTeam(teamName).await()
            if (teams.teams.isNullOrEmpty())
            {
                return@withContext Teams(emptyList())
            }
            return@withContext teams

        }
    }

    suspend fun insertFavTeam(favTeam: FavTeam) {
        return withContext(Dispatchers.IO){
            database.teamDao.insertTeam(favTeam)
        }

    }

    suspend fun getPlayerList(teamId: Long): PlayerList {
        return withContext(Dispatchers.IO) {
            return@withContext SportsAPI.retrofitService.getAllPlayers(teamId).await()
        }
    }

    suspend fun getClubInfo(teamId: Long): Teams {
        return withContext(Dispatchers.IO) {
            return@withContext SportsAPI.retrofitService.getTeamInfo(teamId).await()
        }
    }
}