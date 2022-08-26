package com.appdeviser.tictactoe.gameplay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appdeviser.tictactoe.model.Cell
import com.appdeviser.tictactoe.model.Player

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GamePlayFourCrossFour(
    playerOne: String?,
    playerTwo: String?,
    playerOneScore: Int,
    playerTwoScore: Int,
    startWithPlayerOne: Boolean
) {

    private val TAG = GamePlayFourCrossFour::class.java.simpleName
    private val BOARD_SIZE = 4

    var player1: Player? = null
    var player2: Player? = null

    var currentPlayer = player1
    var cells: Array<Array<Cell?>>? = arrayOf()

    var winner = MutableLiveData<Player>()
    var playerOneScoreLive = MutableLiveData<String>()
    var playerTwoScoreLive = MutableLiveData<String>()
    var actionPlayerOne = MutableLiveData<Boolean>()
    var actionPlayerTwo = MutableLiveData<Boolean>()
    var mSwitchPlayer = false

    init {
        cells = Array<Array<Cell?>>(BOARD_SIZE) { arrayOfNulls(BOARD_SIZE) }
        setArray(cells!!)
        player1 = Player(playerOne, "X")
        player2 = Player(playerTwo, "O")
        currentPlayer = player1
        setActionPlayer(currentPlayer!!)
        playerOneScoreLive.value = playerOneScore.toString()
        playerTwoScoreLive.value = playerTwoScore.toString()
        if (!startWithPlayerOne) changePlayer()
        mSwitchPlayer = false
    }

    /**
     * Set Null array to empty set of array
     */
    private fun setArray(cells: Array<Array<Cell?>>) {
        val initialPlayer = Player("", "")
        for (i in 0 until BOARD_SIZE) {
            for (j in 0 until BOARD_SIZE) {
                cells.get(i)[j] = Cell(initialPlayer)
            }
        }
    }

    /**
     * Switch player turn from playerOne to PlayerTwo and vice-versa
     */
    fun switchPlayer() {
        if(mSwitchPlayer) {
        currentPlayer = if (currentPlayer === player1) player2 else player1
            setActionPlayer(currentPlayer!!)
            mSwitchPlayer = false
        } else {
            mSwitchPlayer = true
        }
    }

    private fun String.updateScore(): String {
        var currentIntScore = this.toInt()
        Log.d(TAG, "Player One Current Score : " + currentIntScore)
        currentIntScore += 1
        return currentIntScore.toString()
    }

    /**
     * check for game is ended or not
     * @return true if any player win or board is full, false otherwise
     */
    fun hasGameEnded(): Boolean {
        Log.d(TAG,"hasGameEnded()")
        if (hasThreeSameHorizontalCells() || hasThreeSameVerticalCells() || hasThreeSameDiagonalCells()) {
            winner.value = currentPlayer
            Log.d(TAG, "Player One : " + currentPlayer?.mName)
            if (currentPlayer == player1) {
                val currentScore = playerOneScoreLive.value
                playerOneScoreLive.value = currentScore?.updateScore()
                // Log.d(TAG,"Player One Score Update: " + playerOneScoreLive.value)
            } else {
                val currentScore = playerTwoScoreLive.value
                playerTwoScoreLive.value = currentScore?.updateScore()
                // Log.d(TAG,"Player Two Score Update: " + playerTwoScoreLive.value)
            }
            mSwitchPlayer = false
            return true
        }

        if (isBoardFull()) {
            winner.value = null
            mSwitchPlayer = false
            return true
        }

        return false
    }

    /**
     * Check horizontal win case for board
     * @return true if any player match win case, false otherwise
     */
    private fun hasThreeSameHorizontalCells(): Boolean {
        try {
            for (i in 0 until BOARD_SIZE)
                if (cells?.get(i)?.get(0)?.let {
                        cells?.get(i)?.get(1)?.let { it1 ->
                            cells?.get(i)?.get(2)?.let { it2 ->
                                cells?.get(i)?.get(3)?.let{ it3 ->
                                    areEqual(
                                        it, it1,
                                        it2, it3)
                                }
                            }
                        }
                    }!!)
                    return true

            return false
        } catch (e: Exception) {
            Log.e(TAG, "Exception thrown at hasThreeSameHorizontalCells()")
            return false
        }

    }

    /**
     * Check vertical win case for board
     * @return true if any player match win case, false otherwise
     */
    private fun hasThreeSameVerticalCells(): Boolean {
        try {
            for (i in 0 until BOARD_SIZE)
                if (cells?.get(0)?.get(i)?.let {
                        cells?.get(1)?.get(i)?.let { it1 ->
                            cells?.get(2)?.get(i)?.let { it2 ->
                                cells?.get(3)?.get(i)?.let{ it3 ->
                                    areEqual(
                                        it, it1,
                                        it2, it3)
                                }
                            }
                        }
                    }!!) {
                    return true
                }
            return false
        } catch (e: Exception) {
            Log.e(TAG, "Exception thrown at hasThreeSameVerticalCells()")
            return false
        }
    }

    /**
     * Check diagonal win case for board
     * @return true if any player match win case, false otherwise
     */
    private fun hasThreeSameDiagonalCells(): Boolean {
        try {
            return cells?.get(0)?.get(0)?.let {
                cells?.get(1)?.get(1)?.let { it1 ->
                    cells?.get(2)?.get(2)?.let { it2 ->
                        cells?.get(3)?.get(3)?.let { it3 ->
                            areEqual(
                                it, it1,
                                it2, it3)
                        }
                    }
                }
            }!! || cells?.get(0)?.get(3)?.let {
                cells?.get(1)?.get(2)?.let { it1 ->
                    cells?.get(2)?.get(1)?.let{ it2 ->
                        cells?.get(3)?.get(0)?.let { it3 ->
                            areEqual(
                                it, it1,
                                it2, it3)
                        }
                    }
                }
            }!!
        } catch (e: Exception) {
            Log.e(TAG, "Exception thrown at hasThreeSameDiagonalCells()")
            return false
        }
    }


    /**
     * Check is board full or not
     * @return true if full, false otherwise
     */
    private fun isBoardFull(): Boolean {
        for (row in this.cells!!)
            for (cell in row)
                if (cell == null || cell.isEmpty())
                    return false
        return true
    }

    /**
     * 2 cells are equal if:
     * - Both are none null
     * - Both have non null values
     * - both have equal values
     *
     * @param cells: Cells to check if are equal
     * @return
     */
    private fun areEqual(vararg cells: Cell): Boolean {
        if (cells.isEmpty())
            return false

        for (cell in cells)
            if (cell.mPlayer?.mValue.equals(null) || cell.mPlayer?.mValue?.length?.equals(0)!!)
            return false

        val comparisonBase = cells[0]
        for (i in 1 until cells.size)
            if (!comparisonBase.mPlayer?.mValue.equals(cells[i].mPlayer?.mValue))
                return false

        return true
    }

    fun reset() {
        player1 = null
        player2 = null
        currentPlayer = null
        cells = arrayOf()
    }

    /**
     * Switch player turn from playerOne to PlayerTwo and vice-versa
     */
    fun changePlayer() {
            currentPlayer = if (currentPlayer === player1) player2 else player1
            setActionPlayer(currentPlayer!!)
    }

    fun setActionPlayer(actionPlayer : Player){
        when(actionPlayer){
            player1 -> {
                actionPlayerOne.value = true
                actionPlayerTwo.value = false
            }

            player2 -> {
                actionPlayerOne.value = false
                actionPlayerTwo.value = true
            }

            else -> {
                actionPlayerOne.value = false
                actionPlayerTwo.value = false
            }
        }
    }
}