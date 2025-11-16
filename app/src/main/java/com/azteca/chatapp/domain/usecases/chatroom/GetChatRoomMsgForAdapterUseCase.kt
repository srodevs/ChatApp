package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.ChatRepositoryImpl
import javax.inject.Inject

class GetChatRoomMsgForAdapterUseCase @Inject constructor(
    private val chatRoomRepository: ChatRepositoryImpl
) {

    operator fun invoke(chatroomId: String) =
        chatRoomRepository.getChatRoomMsgForAdapter(chatroomId)

}