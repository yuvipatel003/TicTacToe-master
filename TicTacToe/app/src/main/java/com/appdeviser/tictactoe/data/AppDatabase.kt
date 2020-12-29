package com.appdeviser.tictactoe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database to keep record of matches between players
 */
@Database(entities = arrayOf(Match::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun matchDao(): MatchDao


    companion object {
        var INSTANCE: AppDatabase? = null

        /**
         * get instance of database to do CRUD operations
         */
        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null){
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context,
                        AppDatabase::class.java, "db_tictactoe").build()
                }
            }
            return INSTANCE
        }

        /**
         * remove instance of database after completing operation
         * to avoid memory leak
         */
        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}

