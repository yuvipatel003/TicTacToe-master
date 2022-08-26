package com.appdeviser.tictactoe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "match")
data class Match(
//    @PrimaryKey(autoGenerate = true) override var id: Int = 0,
////    @ColumnInfo(name = "mode") override var mode: String?,
////    @ColumnInfo(name = "playerone") override var playerone: String?,
////    @ColumnInfo(name = "playertwo") override var playertwo: String?,
////    @ColumnInfo(name = "result") override var result: String?,
////    @ColumnInfo(name = "date") override var date: String?
 @PrimaryKey(autoGenerate = true)
 val id: Int?,
 val mode: String?,
 val playerone: String?,
 val playertwo: String?,
 val result: String?,
 val date: String?
)