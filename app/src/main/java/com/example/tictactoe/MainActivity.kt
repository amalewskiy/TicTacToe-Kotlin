package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tictactoe.play_online.Online
import com.example.tictactoe.play_with_friend.Friend

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val androidBtn: Button = findViewById(R.id.AndroidBtn)
        val friendBtn: Button = findViewById(R.id.FriendBtn)
        val onlineBtn: Button = findViewById(R.id.OnlineBtn)

        androidBtn.setOnClickListener {startActivity(Intent(this, Android::class.java))}
        friendBtn.setOnClickListener {startActivity(Intent(this, Friend::class.java))}
        onlineBtn.setOnClickListener {startActivity(Intent(this, Online::class.java))}
    }
}