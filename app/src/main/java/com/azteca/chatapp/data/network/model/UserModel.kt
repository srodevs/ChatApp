package com.azteca.chatapp.data.network.model

import java.util.Date

data class UserModelResponse(
    var userId: String?,
    var phone: String,
    var username: String,
    val timestamp: Date? = null
) {
    constructor() : this("", "", "", null)
}