package com.example.tictactoe.play_with_friend

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tictactoe.R


class Friend : AppCompatActivity() {

    private lateinit var playerOneScoreText: TextView
    private lateinit var playerTwoScoreText: TextView
    private lateinit var playerOneName: TextView
    private lateinit var playerTwoName: TextView
    private lateinit var status: TextView
    private lateinit var resetGame: Button

    private var scorePlayerOne: Int = 0
    private var scorePlayerTwo: Int = 0
    private var activeOnePlayer: Int = 1

    private var buttons: Array<Button?> = arrayOfNulls(8)

    private fun action(item: Int, userBtn: Button) {
        if (isFree(item, activeOnePlayer, userBtn)) {
            if (isWinning(activeOnePlayer)) {
                if (activeOnePlayer == 1) {
                    scorePlayerOne++
                    playerOneScoreText.text = "$scorePlayerOne"
                    Toast.makeText(this, "${playerOneName.text} is winning", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    scorePlayerTwo++
                    playerTwoScoreText.text = "$scorePlayerTwo"
                    Toast.makeText(this, "${playerTwoName.text} is winning", Toast.LENGTH_SHORT)
                        .show()
                }
                clearBoard(buttons)
                if (activeOnePlayer == 1) status.text = "${playerTwoName.text} starts"
                else status.text = "${playerOneName.text} starts"
            } else if (isListComplete()) {
                Toast.makeText(this, "DRAW!", Toast.LENGTH_SHORT).show()
                clearBoard(buttons)
                if (activeOnePlayer == 1) status.text = "${playerTwoName.text} starts"
                else status.text = "${playerOneName.text} starts"
            }
            activeOnePlayer = kotlin.math.abs(activeOnePlayer - 1)

        }
    }

    private fun reset() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (scorePlayerOne > scorePlayerTwo) {
                    status.text = "${playerOneName.text} is winning game!"
                } else if (scorePlayerOne < scorePlayerTwo) {
                    status.text = "${playerTwoName.text} is winning game!"
                } else status.text = "Draw!!!"
                for (button in buttons) {
                    button?.isClickable = false
                    button?.setBackgroundColor(Color.rgb(114, 115, 114))
                }
            }

            override fun onFinish() {
                if (activeOnePlayer == 1) status.text = "${playerOneName.text} starts"
                else status.text = "${playerTwoName.text} starts"
                for (button in buttons) {
                    button?.isClickable = true
                    button?.setBackgroundColor(Color.rgb(125, 52, 235))
                }
                scorePlayerOne = 0
                scorePlayerTwo = 0
                playerOneScoreText.text = "$scorePlayerOne"
                playerTwoScoreText.text = "$scorePlayerTwo"
                clearBoard(buttons)
            }
        }.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)

        playerOneScoreText = findViewById(R.id.textPlayerOneScore)
        playerTwoScoreText = findViewById(R.id.textPlayerTwoScore)
        playerOneName = findViewById(R.id.textPlayerOne)
        playerTwoName = findViewById(R.id.textPlayerTwo)
        status = findViewById(R.id.textStatus)
        resetGame = findViewById(R.id.btnResetGame)

        val btn_0: Button = findViewById(R.id.btn_0)
        val btn_1: Button = findViewById(R.id.btn_1)
        val btn_2: Button = findViewById(R.id.btn_2)
        val btn_3: Button = findViewById(R.id.btn_3)
        val btn_4: Button = findViewById(R.id.btn_4)
        val btn_5: Button = findViewById(R.id.btn_5)
        val btn_6: Button = findViewById(R.id.btn_6)
        val btn_7: Button = findViewById(R.id.btn_7)
        val btn_8: Button = findViewById(R.id.btn_8)

        buttons = arrayOf(
            btn_0, btn_1, btn_2, btn_3,
            btn_4, btn_5, btn_6, btn_7, btn_8
        )

        btn_0.setOnClickListener { action(0, btn_0) }
        btn_1.setOnClickListener { action(1, btn_1) }
        btn_2.setOnClickListener { action(2, btn_2) }
        btn_3.setOnClickListener { action(3, btn_3) }
        btn_4.setOnClickListener { action(4, btn_4) }
        btn_5.setOnClickListener { action(5, btn_5) }
        btn_6.setOnClickListener { action(6, btn_6) }
        btn_7.setOnClickListener { action(7, btn_7) }
        btn_8.setOnClickListener { action(8, btn_8) }
        resetGame.setOnClickListener { reset() }
    }
}