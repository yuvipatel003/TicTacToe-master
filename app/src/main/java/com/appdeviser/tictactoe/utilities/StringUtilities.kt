package com.appdeviser.tictactoe.utilities

import android.text.TextUtils
import com.appdeviser.tictactoe.data.Match
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

public class StringUtilities {

    companion object {
        val INTENT_PLAYERONE = "PLAYERONE"
        val INTENT_PLAYERTWO = "PLAYERTWO"

        var list : ArrayList<Match>? = ArrayList()

        fun stringFromNumbers(vararg numbers: Int): String {
            val sNumbers = StringBuilder()
            for (number in numbers)
                sNumbers.append(number)
            return sNumbers.toString()
        }

        fun isNullOrEmpty(value: String?): Boolean {
            return value == null || value.length == 0
        }

        fun isValidName(name: String?): Boolean {

            return if (TextUtils.isEmpty(name) || name == null) {
                false
            } else true
        }

        fun setRecord(arraylist : ArrayList<Match>?){
            list = arraylist
        }

        enum class WINNER {
            WINNER_PLAYER_ONE,
            WINNER_PLAYER_TWO,
            WINNER_DRAW
        }

        fun getCurrentDate() : String {
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            return sdf.format(Date())
        }
    }


}