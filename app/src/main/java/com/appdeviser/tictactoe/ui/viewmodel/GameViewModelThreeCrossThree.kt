package com.appdeviser.tictactoe.ui.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.appdeviser.tictactoe.data.AppDatabase
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.data.MatchDao
import com.appdeviser.tictactoe.model.Cell
import com.appdeviser.tictactoe.gameplay.GamePlayThreeCrossThree
import com.appdeviser.tictactoe.model.Player
import com.appdeviser.tictactoe.utilities.StringUtilities.Companion.stringFromNumbers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModelThreeCrossThree(application: Application) : AndroidViewModel(application) {

    private val TAG = GameViewModelThreeCrossThree::class.java.simpleName
    var cells: ObservableArrayMap<String, String> = ObservableArrayMap()
    private var mGamePlayThreeCrossThree: GamePlayThreeCrossThree? = null

    lateinit var mPlayerOneName : String
    lateinit var mPlayerTwoName : String
    val mApplication = application

    val winner: LiveData<Player>
        get() = mGamePlayThreeCrossThree!!.winner

    val playerOneScore: LiveData<String>
        get() = mGamePlayThreeCrossThree!!.playerOneScoreLive

    val playerTwoScore: LiveData<String>
        get() = mGamePlayThreeCrossThree!!.playerTwoScoreLive

    val currentPlayerOne : LiveData<Boolean>
        get() = mGamePlayThreeCrossThree!!.actionPlayerOne

    val currentPlayerTwo : LiveData<Boolean>
        get() = mGamePlayThreeCrossThree!!.actionPlayerTwo

    fun init(player1: String?, player2: String?, playerOneScore : Int, playerTwoScore : Int, startWithPlayerOne : Boolean) {
        mPlayerOneName = player1!!
        mPlayerTwoName = player2!!
        mGamePlayThreeCrossThree =
            GamePlayThreeCrossThree(player1, player2, playerOneScore, playerTwoScore, startWithPlayerOne)
        cells = ObservableArrayMap()
    }

    fun onClickedCellAt(row: Int, column: Int) {
        if (mGamePlayThreeCrossThree!!.cells?.get(row)?.get(column) == null || mGamePlayThreeCrossThree!!.cells?.get(row)?.get(column)?.isEmpty()!!) {
            mGamePlayThreeCrossThree!!.cells?.get(row)?.set(column, Cell(mGamePlayThreeCrossThree!!.currentPlayer))
            cells.put(stringFromNumbers(row,column), mGamePlayThreeCrossThree!!.currentPlayer?.mValue)
            if (mGamePlayThreeCrossThree!!.hasGameEnded())
                mGamePlayThreeCrossThree!!.reset()
            else
                mGamePlayThreeCrossThree!!.switchPlayer()
        }
    }

    fun addMatchResult(currentMatch : Match){

        var mDb: AppDatabase?
        var mMatchDao: MatchDao?

        CoroutineScope(Dispatchers.Default).launch {
            mDb = AppDatabase.getAppDataBase(context = mApplication)

            mMatchDao = mDb?.matchDao()

            val match = currentMatch

            with(mMatchDao) {
                this?.insertAll(match)

                val listOfMatch = mMatchDao?.getAllMatches()

                println("$TAG Total Match :" + listOfMatch?.size)

                // Uncomment below code to get each single match log
                /**
                listOfMatch?.forEach {
                kotlin.io.println(
                "Match:" + "id : " + it.id +
                " p1 : " + it?.playerone +
                " p2 : " + it.playertwo +
                " mode : " + it.mode +
                " result : " + it.result +
                " date : " + it.date
                )

                }*/
            }
        }

    }
 }
