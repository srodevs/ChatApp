package com.azteca.chatapp.domain.usecases.chatroom

import com.azteca.chatapp.data.repository.ChatRepositoryImpl
import javax.inject.Inject

class GetChatUseCase @Inject constructor(
    private val chatRepository: ChatRepositoryImpl
) {
    operator fun invoke() = chatRepository.getChat()

}