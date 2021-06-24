package com.example.tictactoe.play_online

import android.text.Editable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

class DbHelperClass {

    class DbCreateRoomHelper(
        creatorName: Editable,
        roomName: Editable,
        connectorName: String = " ",
        connectorStatus: Boolean = false,
        creatorActive: Int = 1
    ) {
        private val creatorName: String = creatorName.toString()
        private val roomName: String = roomName.toString()
        private val connectorName: String = connectorName
        private val connectorStatus: Boolean = connectorStatus
        private val creatorActive: Int = creatorActive

        fun getCreatorName(): String {
            return creatorName
        }

        fun getRoomName(): String {
            return roomName
        }

        fun getConnectorName(): String {
            return connectorName
        }

        fun getConnectorStatus(): Boolean {
            return connectorStatus
        }

        fun getCreatorActive(): Int {
            return creatorActive
        }
    }

    @IgnoreExtraProperties
    data class Post(
        var connectorName: String? = " ",
        var connectorStatus: Boolean? = false,
        var creatorName: String? = " ",
        var roomName: String? = " ",
        var creatorActive: Int? = 1
    ) {

        @Exclude
        fun toMap(): Map<String, Any?> {
            return mapOf(
                "connectorName" to connectorName,
                "connectorStatus" to connectorStatus,
                "creatorName" to creatorName,
                "roomName" to roomName,
                "creatorActive" to creatorActive
            )
        }
    }
}