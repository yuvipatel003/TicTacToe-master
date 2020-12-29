package com.appdeviser.tictactoe.ui.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.appdeviser.tictactoe.ui.activity.ThreeCrossThree
import com.appdeviser.tictactoe.utilities.StringUtilities
import android.app.Activity
import com.appdeviser.tictactoe.R
import com.appdeviser.tictactoe.ui.activity.FourCrossFour
import com.appdeviser.tictactoe.utilities.StringUtilities.*


class GameEndDialog(context: Context, winnerName: String?,
                    winner: Companion.WINNER) : Dialog(context) {

    private var mContext : Context = context
    private var mStrWinner: String? = winnerName
    private var mWinner : Companion.WINNER = winner
    private val TAG = "TAG_GameEndDialog"

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_gameend)

        val txt_GameResult : TextView = findViewById(R.id.txt_GameResult)
        when(mWinner){
            Companion.WINNER.WINNER_DRAW -> {
                txt_GameResult.text = mContext.getString(R.string.str_draw)
            }
            else -> {
                txt_GameResult.text = mStrWinner +
                        mContext.getString(R.string.str_space) +
                        mContext.getString(R.string.str_winner_is)
            }
        }


        val startNewGameButton : Button? = findViewById(R.id.btnNewGame)
        startNewGameButton?.setOnClickListener { onStartNewGameClicked(mWinner) }

        val mainMenuButton : Button? = findViewById(R.id.btnMainMenu)
        mainMenuButton?.setOnClickListener { goToMainMenu() }

    }

    private fun onStartNewGameClicked(winner : Companion.WINNER) {
        Log.d(TAG, "Start New Game Clicked")
        playAgain(winner, mContext)
        dismiss()
    }

    private fun goToMainMenu() {
        Log.d(TAG, "Main Menu Clicked")
        finishBaseActivity(mContext)
        dismiss()
    }


    private fun playAgain(winner: Companion.WINNER, context: Context) {

        val activity = context as Activity

        Log.d(TAG, "Play again before when")
        when(activity){
            is ThreeCrossThree -> {
                Log.d(TAG, "Play again")
                activity.playAgain(winner)
            }

            is FourCrossFour -> {
                Log.d(TAG, "Play again")
                activity.playAgain(winner)
            }
        }
    }

    private fun finishBaseActivity(context: Context) {

        val activity = context as Activity

        Log.d(TAG, "Finish Activity before when")
        when(activity){
            is ThreeCrossThree -> {
                Log.d(TAG, "Finish Activity -> Go to Main Menu")
                activity.finish()

            }

            is FourCrossFour -> {
                Log.d(TAG, "Finish Activity -> Go to Main Menu")
                activity.finish()
            }
        }
    }
}
