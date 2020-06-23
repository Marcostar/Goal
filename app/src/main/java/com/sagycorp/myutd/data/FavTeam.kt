package com.sagycorp.myutd.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavTeam")
data class FavTeam(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val strTeam: String,
    val strTeamBadge: String,
    val strLeague: String,
    val intFormedYear: String,
    val strStadium:String,
    val strStadiumLocation: String,
    val strCountry:String,
    val intStadiumCapacity:String,
    val strWebsite: String,
    val idTeam: Long)