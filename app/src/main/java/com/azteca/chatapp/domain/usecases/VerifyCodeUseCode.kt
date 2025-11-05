package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.network.AuthFirebaseService
import javax.inject.Inject

class VerifyCodeUseCode @Inject constructor(private val authFirebaseService: AuthFirebaseService) {

    suspend operator fun invoke(verifyId: String, code: String) {
        authFirebaseService.verifyCode(verifyId, code)
    }
}