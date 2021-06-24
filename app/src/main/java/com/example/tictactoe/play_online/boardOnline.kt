package com.example.tictactoe.play_online

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tictactoe.R
import com.google.firebase.database.*
import kotlin.math.abs

class boardOnline : AppCompatActivity() {
    private lateinit var roomNameText: TextView
    private lateinit var playerOneScoreText: TextView
    private lateinit var playerTwoScoreText: TextView
    private lateinit var playerOneName: TextView
    private lateinit var playerTwoName: TextView
    private lateinit var status: TextView
    private lateinit var resetGame: Button

    private val rootNode = FirebaseDatabase.getInstance()
    private val allRoomsReference = rootNode.getReference("rooms")

    private lateinit var myRoomName: String
    private var connectorUserName: String = " "
    private var connectorUserStatus: Boolean? = false

    private var activeUser = arrayOf("host", "guest")
    private var i = 0
    private var user: String = " "
    private var buttons: Array<Button?> = arrayOfNulls(8)

    private fun initAllActivity() {
        roomNameText = findViewById(R.id.textRoomName)
        playerOneScoreText = findViewById(R.id.textPlayerOneScore)
        playerTwoScoreText = findViewById(R.id.textPlayerTwoScore)
        playerOneName = findViewById(R.id.textPlayerOne)
        playerTwoName = findViewById(R.id.textPlayerTwo)
        status = findViewById(R.id.textStatus)
        resetGame = findViewById(R.id.btnResetGame)
    }

    private val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            for (dataSnapshot in snapshot.children) {
                if (dataSnapshot.key.equals(myRoomName)) {
                    val post = dataSnapshot.getValue(DbHelperClass.Post::class.java)
                    roomNameText.text = post?.roomName.toString()
                    playerOneName.text = post?.creatorName.toString()
                    playerTwoName.text = post?.connectorName.toString()
                    connectorUserName = post?.connectorName.toString()
                    connectorUserStatus = post?.connectorStatus
                    break
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    }

    private fun waitingUserThread() {
        Thread(Runnable {
            while (connectorUserStatus == false) {
                allRoomsReference.child(myRoomName).child("connectorStatus").get()
                    .addOnSuccessListener {
                        if (it.value == true) {
                            connectorUserStatus = true
                            allRoomsReference.child(myRoomName).child("connectorName").get()
                                .addOnSuccessListener {
                                    playerTwoName.text = it.value.toString()
                                }
                        }
                    }
                Thread.sleep(1000)
            }
        }).start()
    }

    /*private fun waitingUserInputThread() {
        var feedbackFromUser = " "
        Thread(Runnable {
            while (feedbackFromUser == " ") {
                if (user == "guest") {
                    allRoomsReference.child(myRoomName).child("Game").child("host").get()
                        .addOnSuccessListener {
                            buttons[it.value as Int]?.text = "X"
                            gameState[it.value as Int] = 1
                        }
                } else {
                    allRoomsReference.child(myRoomName).child("Game").child("guest").get()
                        .addOnSuccessListener {
                            buttons[it.value as Int]?.text = "O"
                            gameState[it.value as Int] = 0
                        }
                }
                Thread.sleep(1000)
            }
        }).start()
    }*/

    private fun activateButtons(action: Boolean) {
        for (button in buttons) {
            button?.isClickable = action
        }
    }


    private fun play(item: Int, userBtn: Button) {
        /*if (user == "host") {
            if (isFree(item, i, userBtn, myRoomName)) {
                i = abs(i - 1)
            }
        }*/
    }

    /*private fun waitingUserInput() {
        messageRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val map = snapshot.value
                if (map is Map<*, *>) {
                    if (user == "guest") {
                        buttons[map["host"] as Int]?.text = "X"
                        gameState[map["host"] as Int] = 1
                    } else {
                        buttons[map["guest"] as Int]?.text = "O"
                        gameState[map["guest"] as Int] = 0
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_online)

        initAllActivity()

        myRoomName = intent.getStringExtra("roomName").toString()
        user = intent.getStringExtra("user").toString()
        val messageRef = rootNode.getReference("rooms/${myRoomName}/Game/")

        allRoomsReference.addListenerForSingleValueEvent(postListener)


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
        if (connectorUserStatus == false) waitingUserThread()
        if (user == activeUser[i]) activateButtons(true) else activateButtons(false)

        btn_0.setOnClickListener { play(0, btn_0) }
        btn_1.setOnClickListener { play(1, btn_1) }
        btn_2.setOnClickListener { play(2, btn_2) }
        btn_3.setOnClickListener { play(3, btn_3) }
        btn_4.setOnClickListener { play(4, btn_4) }
        btn_5.setOnClickListener { play(5, btn_5) }
        btn_6.setOnClickListener { play(6, btn_6) }
        btn_7.setOnClickListener { play(7, btn_7) }
        btn_8.setOnClickListener { play(8, btn_8) }

        /*if (user == "guest") {
            activateButtons(false)
            //waitingUserInputThread()
            //waitingUserInput()
            activateButtons(true)
        }*/

        //resetGame.setOnClickListener { allRoomsReference.child(myRoomName).child("Game").child("1_2").setValue("X") }
    }
}
