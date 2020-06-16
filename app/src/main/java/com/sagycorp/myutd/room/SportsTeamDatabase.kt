package com.sagycorp.myutd.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sagycorp.myutd.data.FavTeam

@Database(entities = [FavTeam::class], version = 1, exportSchema = false)
abstract class SportsTeamDatabase  : RoomDatabase() {

    abstract val teamDao: SportsTeamDao

    companion object {

        @Volatile
        private var INSTANCE: SportsTeamDatabase? = null

        fun getInstance(context: Context): SportsTeamDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SportsTeamDatabase::class.java,
                        "sports_team_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}