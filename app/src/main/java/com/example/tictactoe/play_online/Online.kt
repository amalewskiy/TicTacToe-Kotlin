package com.example.tictactoe.play_online

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.R
import com.google.firebase.database.*


class Online : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var nameRoom: EditText
    private lateinit var createRoom: Button
    private lateinit var connectToRoom: Button

    private val rootNode = FirebaseDatabase.getInstance()
    private val allRoomsReference = rootNode.getReference("rooms")

    private fun createNewRoom(creatorName: Editable, roomName: Editable) {
        val helperClass: DbHelperClass.DbCreateRoomHelper =
            DbHelperClass.DbCreateRoomHelper(creatorName, roomName)
        allRoomsReference.child(roomName.toString()).setValue(helperClass)
        allRoomsReference.child(roomName.toString()).child("Game").child("host").setValue(0)
        allRoomsReference.child(roomName.toString()).child("Game").child("guest").setValue(0)
        val intent = Intent(this, boardOnline::class.java)
        intent.putExtra("roomName", roomName.toString())
        intent.putExtra("user", "host")
        startActivity(intent)
    }

    private fun connectToRoom(userName: Editable, roomName: Editable) {
        allRoomsReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    if (dataSnapshot.key.equals(roomName.toString())) {
                        allRoomsReference.child(roomName.toString()).child("connectorName")
                            .setValue(userName.toString())
                        allRoomsReference.child(roomName.toString()).child("connectorStatus")
                            .setValue(true)
                        val intent = Intent(this@Online, boardOnline::class.java)
                        intent.putExtra("roomName", roomName.toString())
                        intent.putExtra("user", "guest")
                        startActivity(intent)
                        break
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online)
        userName = findViewById(R.id.editTextCreatorName)
        nameRoom = findViewById(R.id.editTextNameRoom)
        createRoom = findViewById(R.id.btnCreateRoom)
        connectToRoom = findViewById(R.id.btnConnectToRoom)

        createRoom.setOnClickListener { createNewRoom(userName.text, nameRoom.text) }
        connectToRoom.setOnClickListener { connectToRoom(userName.text, nameRoom.text) }

    }
}