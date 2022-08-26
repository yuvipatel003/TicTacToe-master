package com.appdeviser.tictactoe.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.appdeviser.tictactoe.data.AppDatabase
import com.appdeviser.tictactoe.data.Match
import com.appdeviser.tictactoe.data.MatchDao
import kotlinx.coroutines.*

class RecentResultViewModel(application: Application) : AndroidViewModel(application){

    private val TAG = RecentResultViewModel::class.java.simpleName
    val recentResultList = MutableLiveData<ArrayList<Match>>()
    private var mDb: AppDatabase? = null
    private var mApplication = application

    // Handle co-routine exceptions using CoroutineExceptionHandler
    private val exceptionHandler = CoroutineExceptionHandler {   context, exception ->
        Log.d(TAG,"Exception : " + exception.toString())
    }

    init {
        mDb = AppDatabase.getAppDataBase(mApplication)
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {

            val matchDao: MatchDao? = mDb?.matchDao()
            val listOfMatch: ArrayList<Match>?

            listOfMatch = matchDao?.getRecentMatches() as ArrayList<Match>?

            println("Total Match :" + listOfMatch?.size)

            setRecentResultList(listOfMatch)

            // Uncomment below code to get each single match log
            /**
            listOfMatch?.forEach {
            kotlin.io.println(
            "Match:" + "id : " + it.id +
            " p1 : " + it?.playerone +
            " p2 : " + it.playertwo +
            " mode : " + it.mode +
            " result : " + it.result +
            " date : " + it.date)
            }*/
        }
    }

    /**
     * Call setList function on main thread
     */
    private suspend fun setRecentResultList(list : ArrayList<Match>?){
        withContext(Dispatchers.Main) {
            setList(list)
        }
    }

    /**
     * set Match list
     * @param list - List of matches
     */
    private fun setList(list : ArrayList<Match>?) {
        Log.d(TAG, "setList()")
        recentResultList.value = list
    }

    /**
     * Load all matches
     */
    fun getAllMatches(){
        mDb = AppDatabase.getAppDataBase(mApplication)
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {

            val matchDao: MatchDao? = mDb?.matchDao()
            val listOfMatch: ArrayList<Match>?

            listOfMatch = matchDao?.getAllMatches() as ArrayList<Match>?

            println("Total Match :" + listOfMatch?.size)

            setRecentResultList(listOfMatch)

            // Uncomment below code to get each single match log
            /**
            listOfMatch?.forEach {
            kotlin.io.println(
            "Match:" + "id : " + it.id +
            " p1 : " + it?.playerone +
            " p2 : " + it.playertwo +
            " mode : " + it.mode +
            " result : " + it.result +
            " date : " + it.date)
            }*/
        }
    }

}