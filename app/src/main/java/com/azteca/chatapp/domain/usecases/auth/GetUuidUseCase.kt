package com.azteca.chatapp.domain.usecases.auth

import com.azteca.chatapp.data.repository.AuthRepositoryImpl
import javax.inject.Inject

class GetUuidUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
) {

    operator fun invoke() = authRepositoryImpl.getUuid()

}