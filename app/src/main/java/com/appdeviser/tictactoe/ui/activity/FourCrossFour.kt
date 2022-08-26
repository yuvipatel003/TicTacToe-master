package com.appdeviser.tictactoe.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appdeviser.tictactoe.R
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.databinding.ActivityFourCrossFourBinding
import com.appdeviser.tictactoe.databinding.ActivityThreeCrossThreeBinding
import com.appdeviser.tictactoe.model.Player
import com.appdeviser.tictactoe.ui.view.GameEndDialog
import com.appdeviser.tictactoe.ui.viewmodel.GameViewModelFourCrossFour
import com.appdeviser.tictactoe.ui.viewmodel.GameViewModelThreeCrossThree
import com.appdeviser.tictactoe.utilities.StringUtilities
import kotlinx.android.synthetic.main.activity_three_cross_three.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FourCrossFour : AppCompatActivity() {

    private val TAG = FourCrossFour::class.java.simpleName
    private val NO_WINNER = "Draw"
    private val MODE = "4 x 4"
    private var mGameViewModelFourCrossFour: GameViewModelFourCrossFour? = null
    private var mNamePlayerOne: String? = null
    private var mNamePlayerTwo:String? = null
    private var mPlayerOneScore = 0
    private var mPlayerTwoScore = 0
    private var mStartWithPlayerOne = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four_cross_four)

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
        val activityMainBinding : ActivityFourCrossFourBinding

        activityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_four_cross_four
        )
        mGameViewModelFourCrossFour = ViewModelProvider(this).get(GameViewModelFourCrossFour::class.java)
        mGameViewModelFourCrossFour!!.init(player1, player2, mPlayerOneScore, mPlayerTwoScore, mStartWithPlayerOne)
        activityMainBinding.setGameViewModel(mGameViewModelFourCrossFour)
        activityMainBinding.setLifecycleOwner(this)

        setUpOnGameEndListener()
    }

    private fun setUpOnGameEndListener() {
        // set observer for winner
        val winnerName = Observer<Player> { player ->
            // Update the UI, in this case, a TextView.
            onGameWinnerChanged(player)
        }
        mGameViewModelFourCrossFour?.winner?.observe(this,winnerName)

        // set observer for playerOneScore
        val playerOneScoreUpdate = Observer<String> { score ->
            // Update the UI, in this case, a TextView.
            onPlayerOneScoreUpdate(score)
        }
        mGameViewModelFourCrossFour?.playerOneScore?.observe(this,playerOneScoreUpdate)

        // set observer for playerTwoScore
        val playerTwoScoreUpdate = Observer<String> { score ->
            // Update the UI, in this case, a TextView.
            onPlayerTwoScoreUpdate(score)
        }
        mGameViewModelFourCrossFour?.playerTwoScore?.observe(this,playerTwoScoreUpdate)
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
            if (winner == null || StringUtilities.isNullOrEmpty(winner.mName)) NO_WINNER else winner.mName

        var winnerPlayer : StringUtilities.Companion.WINNER = StringUtilities.Companion.WINNER.WINNER_DRAW
        when(winnerName) {
            mNamePlayerOne -> winnerPlayer = StringUtilities.Companion.WINNER.WINNER_PLAYER_ONE
            mNamePlayerTwo -> winnerPlayer = StringUtilities.Companion.WINNER.WINNER_PLAYER_TWO
        }

        Log.d(TAG, "Winner is :$winnerName")
        val dialog = GameEndDialog(this,winnerName,winnerPlayer)
        dialog.show()


        var match = Match(null, mode = MODE, playerone = mNamePlayerOne, playertwo = mNamePlayerTwo,
            result = winnerName, date = StringUtilities.getCurrentDate())

        // Insert match detail in database
        mGameViewModelFourCrossFour?.addMatchResult(match)
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
