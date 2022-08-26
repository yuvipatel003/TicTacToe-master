package com.appdeviser.tictactoe.model

import com.appdeviser.tictactoe.utilities.StringUtilities

class Cell (player : Player?){
    var mPlayer : Player? = player

    fun setPlayer (player : Player?) {
        this.mPlayer = player
    }

    fun isEmpty() : Boolean {
        return mPlayer == null || StringUtilities.isNullOrEmpty(mPlayer?.mValue)
    }
}