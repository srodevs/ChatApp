package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.ChatRepositoryImpl
import com.azteca.chatapp.domain.model.ChatMsgModel
import javax.inject.Inject

class GetChatRoomMsgUseCase @Inject constructor(
    private val chatRoomRepository: ChatRepositoryImpl
) {

    operator fun invoke(chatRoomId: String, chatModel: ChatMsgModel): Boolean =
        chatRoomRepository.getChatRoomMsg(chatRoomId, chatModel)

}