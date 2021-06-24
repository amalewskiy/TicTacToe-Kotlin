package com.example.tictactoe.play_with_friend

import android.widget.Button

val winningPosition = arrayOf<IntArray>(
    intArrayOf(0, 1, 2), intArrayOf(3, 4, 5),
    intArrayOf(6, 7, 8), intArrayOf(0, 3, 6),
    intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
    intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
)
val gameState = arrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

fun isFree(item: Int, activeOnePlayer: Int, userBtn: Button): Boolean {
    if (gameState[item] == 2) {
        gameState[item] = activeOnePlayer
        if (activeOnePlayer == 1) userBtn.text = "X" else userBtn.text = "O"
        return true
    }
    return false
}

fun isWinning(player: Int): Boolean {
    for (i in winningPosition) {
        if (gameState[i[0]] == player &&
            gameState[i[1]] == player &&
            gameState[i[2]] == player
        ) return true
    }
    return false
}

fun clearBoard(buttons: Array<Button?>) {
    for (i in buttons.indices) {
        buttons[i]?.text = " "
        gameState[i] = 2
    }
}

fun isListComplete(): Boolean {
    return gameState.indexOf(2) == -1
}