package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.ChatRepositoryImpl
import javax.inject.Inject

class GetChatRoomIdUseCase @Inject constructor(
    private val chatRepositoryImpl: ChatRepositoryImpl
) {
    operator fun invoke(otherUserId: String): String =
        chatRepositoryImpl.getChatRoomId(otherUserId)
}