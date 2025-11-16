package com.azteca.chatapp.domain.interfaz

import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {

    fun getUuid(): String
    fun logout()
    suspend fun verifyCode(verifyId: String, code: String)
    fun loginPhone(
        string: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    )

}