package com.appdeviser.tictactoe.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.appdeviser.tictactoe.R
import com.appdeviser.tictactoe.utilities.StringUtilities
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {


    val TAG = "TAG_HomeActivity"
    var mPlayerOneName : String = ""
    var mPlayerTwoName : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // handle buttons click
        btnPlayThreeCrossThree.setOnClickListener(this)
        btnPlayFourCrossFour.setOnClickListener(this)
        //btnPlayFiveCrossFive.setOnClickListener(this)

        // Open Result activity to display records or result
        btnRecentResults.setOnClickListener{
            Log.d(TAG,"Result pressed")
            var intent = Intent(this, RecentResultActivity::class.java)
            startGame(intent, true)
        }

        constraintLayoutHome.setOnClickListener {
            it.hideKeyboard()
        }
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    /**
     * View onClick to handle all click events of mode buttons
     */
    override fun onClick(v: View?) {
        if (!isPlayerDetailsSet()) {
            Toast.makeText(this.applicationContext, "Player details missing", Toast.LENGTH_SHORT).show()
            return
        } else {
            when(v?.getId()) {

                btnPlayThreeCrossThree.id -> {
                    var intent = Intent(this, ThreeCrossThree::class.java)
                    startGame(intent, false)
                    Log.d (TAG, "3 x 3 pressed")
                }

                btnPlayFourCrossFour.id ->  {
                    var intent = Intent(this, FourCrossFour::class.java)
                    startGame(intent, false)
                    Log.d(TAG,"4 x 4 pressed")
                }

              //  btnPlayFiveCrossFive.id -> Log.d(TAG,"5 x 5 pressed")
            }
        }
    }

    /**
     * Start GamePlayThreeCrossThree(Start activity) using intent
     * @param intent - intent to start activity (such as ThreeCrossThree for mode 3 x 3)
     */
    private fun startGame(intent: Intent, isOpenResultActivity : Boolean) {
        if(!isOpenResultActivity) {
            intent.putExtra(StringUtilities.INTENT_PLAYERONE, mPlayerOneName)
            intent.putExtra(StringUtilities.INTENT_PLAYERTWO, mPlayerTwoName)
        }
        startActivity(intent)
    }

    /**
     * Set Player details
     * @return true if players name is not null and not empty, false otherwise
     */
    fun isPlayerDetailsSet() : Boolean {

        mPlayerOneName = editTextPlayerOne.text.toString()
        mPlayerTwoName = editTextPlayerTwo.text.toString()

        return !(mPlayerOneName.isNullOrBlank() ||
                mPlayerTwoName.isNullOrBlank())
    }

}
