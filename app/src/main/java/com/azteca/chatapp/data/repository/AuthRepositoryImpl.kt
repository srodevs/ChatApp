package com.azteca.chatapp.data.repository

import android.app.Activity
import com.azteca.chatapp.data.network.service.AuthFirebaseService
import com.azteca.chatapp.domain.interfaz.AuthRepository
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authFirebaseService: AuthFirebaseService,
) : AuthRepository {

    override fun getUuid(): String = authFirebaseService.getCurrentUid().orEmpty()

    override fun logout() {
        authFirebaseService.logOut()
    }

    override suspend fun verifyCode(verifyId: String, code: String) {
        authFirebaseService.verifyCode(verifyId, code)
    }

    override fun loginPhone(
        string: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        authFirebaseService.loginPhone(string, activity, callback)
    }
}