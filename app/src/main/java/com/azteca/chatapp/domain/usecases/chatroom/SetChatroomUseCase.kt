package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.ChatRepositoryImpl
import com.azteca.chatapp.domain.model.ChatroomModel
import javax.inject.Inject

class SetChatroomUseCase @Inject constructor(
    private val chatRepositoryImpl: ChatRepositoryImpl,
) {

    operator fun invoke(chatroomId: String, chatSend: ChatroomModel) {
        chatRepositoryImpl.setChatroom(chatroomId, chatSend)
    }

}