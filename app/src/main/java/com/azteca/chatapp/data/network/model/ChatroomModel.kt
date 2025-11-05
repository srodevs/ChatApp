package com.azteca.chatapp.data.network.model

import java.sql.Timestamp
import java.util.Date

data class ChatroomModel(
    var chatroomId: String,
    var listUser: List<String>,
    var timestamp: Timestamp,
    var lastMsgSenderId: String,
    var lastMsg: String,
)

data class ChatroomModelResponse(
    var chatroomId: String,
    var listUser: List<String>,
    var timestamp: Date?,
    var lastMsgSenderId: String,
    var lastMsg: String,
) {
    constructor() : this("", emptyList(), null, "", "")
}