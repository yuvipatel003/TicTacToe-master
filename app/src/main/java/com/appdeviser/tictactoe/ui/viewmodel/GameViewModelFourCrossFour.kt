package com.appdeviser.tictactoe.ui.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.appdeviser.tictactoe.data.AppDatabase
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.data.MatchDao
import com.appdeviser.tictactoe.gameplay.GamePlayFourCrossFour
import com.appdeviser.tictactoe.model.Cell
import com.appdeviser.tictactoe.gameplay.GamePlayThreeCrossThree
import com.appdeviser.tictactoe.model.Player
import com.appdeviser.tictactoe.utilities.StringUtilities.Companion.stringFromNumbers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModelFourCrossFour(application: Application) : AndroidViewModel(application) {

    private val TAG = GameViewModelFourCrossFour::class.java.simpleName
    var cells: ObservableArrayMap<String, String> = ObservableArrayMap()
    private var mGamePlayFourCrossFour: GamePlayFourCrossFour? = null

    lateinit var mPlayerOneName : String
    lateinit var mPlayerTwoName : String
    val mApplication = application

    val winner: LiveData<Player>
        get() = mGamePlayFourCrossFour!!.winner

    val playerOneScore: LiveData<String>
        get() = mGamePlayFourCrossFour!!.playerOneScoreLive

    val playerTwoScore: LiveData<String>
        get() = mGamePlayFourCrossFour!!.playerTwoScoreLive

    val currentPlayerOne : LiveData<Boolean>
        get() = mGamePlayFourCrossFour!!.actionPlayerOne

    val currentPlayerTwo : LiveData<Boolean>
        get() = mGamePlayFourCrossFour!!.actionPlayerTwo


    fun init(player1: String?, player2: String?, playerOneScore : Int, playerTwoScore : Int, startWithPlayerOne : Boolean) {
        mPlayerOneName = player1!!
        mPlayerTwoName = player2!!
        mGamePlayFourCrossFour =
            GamePlayFourCrossFour(player1, player2, playerOneScore, playerTwoScore, startWithPlayerOne)
        cells = ObservableArrayMap()
    }

    fun onClickedCellAt(row: Int, column: Int) {
        if (mGamePlayFourCrossFour!!.cells?.get(row)?.get(column) == null || mGamePlayFourCrossFour!!.cells?.get(row)?.get(column)?.isEmpty()!!) {
            mGamePlayFourCrossFour!!.cells?.get(row)?.set(column, Cell(mGamePlayFourCrossFour!!.currentPlayer))
            cells.put(stringFromNumbers(row,column), mGamePlayFourCrossFour!!.currentPlayer?.mValue)
            if (mGamePlayFourCrossFour!!.hasGameEnded())
                mGamePlayFourCrossFour!!.reset()
            else
                mGamePlayFourCrossFour!!.switchPlayer()
        }
    }

    fun addMatchResult(currentMatch : Match){

        var mDb: AppDatabase?
        var mMatchDao: MatchDao?

        CoroutineScope(Dispatchers.Default).launch {
            mDb = AppDatabase.getAppDataBase(context = mApplication)

            mMatchDao = mDb?.matchDao()

            var match = currentMatch

            with(mMatchDao) {
                this?.insertAll(match)

                var listOfMatch = mMatchDao?.getAllMatches()

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
