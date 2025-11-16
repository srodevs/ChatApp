package com.azteca.chatapp.ui.main.fragment.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azteca.chatapp.data.network.model.ChatMsgModelResponse
import com.azteca.chatapp.data.network.model.ChatroomModelResponse
import com.azteca.chatapp.domain.model.ChatMsgModel
import com.azteca.chatapp.domain.model.ChatroomModel
import com.azteca.chatapp.domain.usecases.auth.GetUuidUseCase
import com.azteca.chatapp.domain.usecases.chatroom.GetChatRoomIdUseCase
import com.azteca.chatapp.domain.usecases.chatroom.GetChatRoomMsgForAdapterUseCase
import com.azteca.chatapp.domain.usecases.chatroom.GetChatRoomMsgUseCase
import com.azteca.chatapp.domain.usecases.chatroom.GetChatRoomUseCase
import com.azteca.chatapp.domain.usecases.chatroom.SetChatroomUseCase
import com.azteca.chatapp.domain.usecases.user.GetImgUserCase
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getChatRoomIdUseCase: GetChatRoomIdUseCase,
    private val getImgUserCase: GetImgUserCase,
    private val getChatRoomUseCase: GetChatRoomUseCase,
    private val getChatRoomMsgUseCase: GetChatRoomMsgUseCase,
    private val getCurrentUserUuid: GetUuidUseCase,
    private val setChatroomUse: SetChatroomUseCase,
    private val getChatRoomMsfAdapter: GetChatRoomMsgForAdapterUseCase
) : ViewModel() {

    fun getChatRoomId(
        otherUserId: String,
        response: (String) -> Unit,
        responseImg: (String) -> Unit
    ) {
        viewModelScope.launch {
            response(getChatRoomIdUseCase.invoke(otherUserId))
            responseImg(getImgUserCase.invoke(otherUserId))
        }
    }

    fun getChatroom(
        chatroomId: String,
        otherUserId: String,
        chatRoomRes: (ChatroomModelResponse?) -> Unit
    ) {
        viewModelScope.launch {
            val res = getChatRoomUseCase.invoke(chatroomId, otherUserId)
            if (res.first) {
                chatRoomRes(res.second)
            } else {
                val chatSend = ChatroomModel(
                    chatroomId,
                    listOf(getCurrentUserUuid.invoke(), otherUserId),
                    Timestamp(System.currentTimeMillis()),
                    "",
                    ""
                )
                setChatroom(chatroomId, chatSend, false)
            }
        }
    }

    fun setChatroom(chatroomId: String, chatSend: ChatroomModel, valSendMsg: Boolean) {
        viewModelScope.launch {
            if (valSendMsg) {
                val mChat = ChatroomModel(
                    chatroomId = chatroomId,
                    listUser = listOf(
                        getCurrentUserUuid.invoke(),
                        chatSend.listUser[1]
                    ),
                    timestamp = Timestamp(System.currentTimeMillis()),
                    lastMsgSenderId = getCurrentUserUuid.invoke(),
                    lastMsg = chatSend.lastMsg
                )
                setChatroomUse.invoke(chatroomId, mChat)
            } else {
                setChatroomUse.invoke(chatroomId, chatSend)
            }
        }
    }

    fun getChatRoomMsg(
        chatroomId: String,
        chatMsgModel: ChatMsgModel,
        response: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            chatMsgModel.senderId = getCurrentUserUuid.invoke()
            response(getChatRoomMsgUseCase.invoke(chatroomId, chatMsgModel))
        }
    }

    fun getChatRoomMsgForAdapter(
        chatroomId: String,
        res: (FirestoreRecyclerOptions<ChatMsgModelResponse>) -> Unit
    ) {
        viewModelScope.launch {
            res(getChatRoomMsfAdapter.invoke(chatroomId))
        }
    }

}