package com.azteca.chatapp.domain.model

import java.sql.Timestamp

data class ChatroomModel(
    var chatroomId: String,
    var listUser: List<String>,
    var timestamp: Timestamp,
    var lastMsgSenderId: String,
    var lastMsg: String,
)
