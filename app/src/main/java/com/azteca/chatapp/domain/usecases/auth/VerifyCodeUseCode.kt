package com.azteca.chatapp.domain.usecases.auth

import com.azteca.chatapp.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class VerifyCodeUseCode @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) {

    suspend operator fun invoke(verifyId: String, code: String) {
        authRepository.verifyCode(verifyId, code)
    }
}