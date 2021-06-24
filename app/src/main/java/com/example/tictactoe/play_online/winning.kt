package com.example.tictactoe.play_online

import android.widget.Button
import com.google.firebase.database.FirebaseDatabase

private val rootNode = FirebaseDatabase.getInstance()
private val allRoomsReference = rootNode.getReference("rooms")

val winningPosition = arrayOf<IntArray>(
    intArrayOf(0, 1, 2), intArrayOf(3, 4, 5),
    intArrayOf(6, 7, 8), intArrayOf(0, 3, 6),
    intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
    intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
)
val gameState = arrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)

fun isFree(item: Int, activeCreator: Int, userBtn: Button?, myRoomName: String): Boolean {
        if (gameState[item] == 2) {
            gameState[item] = activeCreator
            allRoomsReference.child(myRoomName).child("Game").child(activeCreator.toString())
                .setValue(item)
            if (activeCreator == 0) userBtn?.text = "X" else userBtn?.text = "O"
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