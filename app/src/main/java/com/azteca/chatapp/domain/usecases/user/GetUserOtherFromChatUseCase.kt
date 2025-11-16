package com.azteca.chatapp.domain.usecases.user

import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.data.repository.UserRepositoryImpl
import javax.inject.Inject

class GetUserOtherFromChatUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {
    suspend operator fun invoke(
        uuid: String,
        listUser: List<String>
    ): UserModelResponse? {
        return userRepository.getOtherUserFromChat(uuid, listUser)
    }

}