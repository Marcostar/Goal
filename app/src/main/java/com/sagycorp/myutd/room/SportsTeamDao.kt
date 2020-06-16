package com.sagycorp.myutd.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sagycorp.myutd.data.FavTeam
import com.sagycorp.myutd.data.Teams

@Dao
interface SportsTeamDao {

    @Query("select * from FavTeam")
    fun getYourTeam(): LiveData<List<FavTeam>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: FavTeam)

}