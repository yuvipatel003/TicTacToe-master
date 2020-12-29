package com.appdeviser.tictactoe.model

class Player (name : String?, value : String?){

    var mName : String? = name
    var mValue : String? = value

    fun setPlayer (name: String, value : String) {
        this.mName = name
        this.mValue = value
    }
}