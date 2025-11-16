package com.azteca.chatapp.domain.usecases.auth

import com.azteca.chatapp.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
) {

    operator fun invoke() {
        authRepository.logout()
    }

}