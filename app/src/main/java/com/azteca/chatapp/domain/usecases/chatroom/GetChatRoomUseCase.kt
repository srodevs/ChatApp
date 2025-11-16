package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.ChatRepositoryImpl
import javax.inject.Inject

class GetChatRoomUseCase @Inject constructor(
    private val chatRoomRepository: ChatRepositoryImpl
) {

    suspend operator fun invoke(chatRoomId: String, otherUserId: String) =
        chatRoomRepository.getChatroom(chatRoomId, otherUserId)

}