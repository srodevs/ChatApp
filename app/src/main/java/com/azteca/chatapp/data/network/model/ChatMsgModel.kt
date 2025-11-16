package com.azteca.chatapp.data.network.model

import java.util.Date
data class ChatMsgModelResponse(
    var msg: String,
    var senderId: String,
    var timestamp: Date?,
) {
    constructor() : this("", "", null)
}
