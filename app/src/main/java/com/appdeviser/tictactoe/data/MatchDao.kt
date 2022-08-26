package com.appdeviser.tictactoe.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MatchDao {
    @Query("SELECT * FROM `match` ORDER BY id DESC LIMIT 5")
    fun getRecentMatches(): List<Match>

    @Query("SELECT * FROM `match` ORDER BY id DESC")
    fun getAllMatches(): List<Match>

    @Query("SELECT * FROM `match` WHERE id IN (:id)")
    fun loadMatchIds(id: IntArray): List<Match>

    @Insert
    fun insertAll(match: Match)

    @Query("DELETE FROM `match`")
    fun deleteAll()
}