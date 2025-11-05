package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.network.AuthFirebaseService
import javax.inject.Inject

class GetUuidUseCase @Inject constructor(private val authFirebaseService: AuthFirebaseService) {

    operator fun invoke() = authFirebaseService.getCurrentUid()

}