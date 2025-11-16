package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.UserRepositoryImpl
import javax.inject.Inject

class GetOtherUserFromChatRoomUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {
    suspend operator fun invoke(
        uuid: String,
        listUser: List<String>,
    ) = userRepository.getOtherUserFromChat(uuid, listUser)

}