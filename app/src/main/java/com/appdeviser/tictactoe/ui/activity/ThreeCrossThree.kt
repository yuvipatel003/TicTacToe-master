package com.appdeviser.tictactoe.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appdeviser.tictactoe.model.Player
import com.appdeviser.tictactoe.R
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.databinding.ActivityThreeCrossThreeBinding
import com.appdeviser.tictactoe.utilities.StringUtilities
import com.appdeviser.tictactoe.utilities.StringUtilities.Companion.isNullOrEmpty
import com.appdeviser.tictactoe.ui.view.GameEndDialog
import com.appdeviser.tictactoe.ui.viewmodel.GameViewModelThreeCrossThree
import kotlinx.android.synthetic.main.activity_three_cross_three.*

class ThreeCrossThree : AppCompatActivity() {

    private val TAG = "TAG_ThreeCrossThree_Activity"
    private val NO_WINNER = "Draw"
    private val MODE = "3 x 3"
    private var mGameViewModelThreeCrossThree: GameViewModelThreeCrossThree? = null
    private var mNamePlayerOne: String? = null
    private var mNamePlayerTwo:String? = null
    private var mPlayerOneScore = 0
    private var mPlayerTwoScore = 0
    private var mStartWithPlayerOne = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three_cross_three)

        if(intent.getStringExtra(StringUtilities.INTENT_PLAYERONE) != null ||
            intent.getStringExtra(StringUtilities.INTENT_PLAYERTWO) != null) {

            mNamePlayerOne = intent.getStringExtra(StringUtilities.INTENT_PLAYERONE)
            mNamePlayerTwo = intent.getStringExtra(StringUtilities.INTENT_PLAYERTWO)

            Log.d(TAG, "Player name :" + mNamePlayerOne)

            textDisplayPlayerOneScore.text = mPlayerOneScore.toString()
            textDisplayPlayerTwoScore.text = mPlayerTwoScore.toString()

            initDataBinding(mNamePlayerOne, mNamePlayerTwo)
        } else {
            Log.d(TAG,"Intent is null")
        }
    }

    private fun initDataBinding(player1: String?, player2: String?) {
        val activityMainBinding : ActivityThreeCrossThreeBinding

        activityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_three_cross_three
        )
        mGameViewModelThreeCrossThree = ViewModelProvider(this).get(GameViewModelThreeCrossThree::class.java)
        mGameViewModelThreeCrossThree!!.init(player1, player2, mPlayerOneScore, mPlayerTwoScore, mStartWithPlayerOne)
        activityMainBinding.setGameViewModel(mGameViewModelThreeCrossThree)
        activityMainBinding.setLifecycleOwner(this)

        setUpOnGameEndListener()
    }

    private fun setUpOnGameEndListener() {
        // set observer for winner
        val winnerName = Observer<Player> { player ->
            // Update the UI, in this case, a TextView.
            onGameWinnerChanged(player)
        }
        mGameViewModelThreeCrossThree?.winner?.observe(this,winnerName)

        // set observer for playerOneScore
        val playerOneScoreUpdate = Observer<String> { score ->
            // Update the UI, in this case, a TextView.
            onPlayerOneScoreUpdate(score)
        }
        mGameViewModelThreeCrossThree?.playerOneScore?.observe(this,playerOneScoreUpdate)

        // set observer for playerTwoScore
        val playerTwoScoreUpdate = Observer<String> { score ->
            // Update the UI, in this case, a TextView.
            onPlayerTwoScoreUpdate(score)
        }
        mGameViewModelThreeCrossThree?.playerTwoScore?.observe(this,playerTwoScoreUpdate)

    }

    @VisibleForTesting
    fun onPlayerOneScoreUpdate(score: String) {
        mPlayerOneScore = score.toInt()
        Log.d(TAG, "Player One Score : " + score)
        textDisplayPlayerOneScore.text = score
    }

    @VisibleForTesting
    fun onPlayerTwoScoreUpdate(score: String) {
        mPlayerTwoScore = score.toInt()
        Log.d(TAG, "Player Two Score : " + score)
        textDisplayPlayerTwoScore.text = score
    }

    @VisibleForTesting
    fun onGameWinnerChanged(winner: Player?) {
        val winnerName =
            if (winner == null || isNullOrEmpty(winner.mName)) NO_WINNER else winner.mName

        var winnerPlayer : StringUtilities.Companion.WINNER = StringUtilities.Companion.WINNER.WINNER_DRAW
        when(winnerName) {
            mNamePlayerOne -> winnerPlayer = StringUtilities.Companion.WINNER.WINNER_PLAYER_ONE
            mNamePlayerTwo -> winnerPlayer = StringUtilities.Companion.WINNER.WINNER_PLAYER_TWO
        }

        Log.d(TAG, "Winner is :$winnerName")
        val dialog = GameEndDialog(this,winnerName,winnerPlayer)
        dialog.show()

        val match = Match(null, mode = MODE, playerone = mNamePlayerOne, playertwo = mNamePlayerTwo,
            result = winnerName, date = StringUtilities.getCurrentDate())

        // Insert match detail in database
        mGameViewModelThreeCrossThree?.addMatchResult(match)
    }

    fun playAgain(winner : StringUtilities.Companion.WINNER) {
        when(winner){
            StringUtilities.Companion.WINNER.WINNER_PLAYER_ONE -> mStartWithPlayerOne = false
            StringUtilities.Companion.WINNER.WINNER_PLAYER_TWO -> mStartWithPlayerOne = true
            else -> Log.d(TAG,"Draw")
        }
        initDataBinding(mNamePlayerOne, mNamePlayerTwo)
    }
}

