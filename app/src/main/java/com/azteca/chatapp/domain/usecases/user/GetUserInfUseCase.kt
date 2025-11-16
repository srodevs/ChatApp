package com.azteca.chatapp.domain.usecases.user

import com.azteca.chatapp.data.repository.UserRepositoryImpl
import javax.inject.Inject

class GetUserInfUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {

    suspend operator fun invoke() = userRepository.getUserInf()

}