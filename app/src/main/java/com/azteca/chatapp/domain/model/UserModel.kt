package com.azteca.chatapp.domain.model

import java.sql.Timestamp

data class UserModel(
    var userId: String?,
    var phone: String,
    var username: String,
    var timestamp: Timestamp?,
    var fcmToken: String,
)