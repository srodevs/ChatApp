package com.azteca.chatapp.domain.interfaz

import com.azteca.chatapp.data.network.model.ChatMsgModelResponse
import com.azteca.chatapp.data.network.model.ChatroomModelResponse
import com.azteca.chatapp.domain.model.ChatMsgModel
import com.azteca.chatapp.domain.model.ChatroomModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions

interface ChatRoomRepository {

    fun getChatRoomId(
        otherUserId: String,
    ): String

    suspend fun getChatroom(
        chatroomId: String,
        otherUserId: String,
    ): Pair<Boolean, ChatroomModelResponse?>

    fun setChatroom(chatroomId: String, chatSend: ChatroomModel)

    fun getChatRoomMsg(
        chatroomId: String,
        chatMsgModel: ChatMsgModel,
    ): Boolean

    fun getChatRoomMsgForAdapter(
        chatroomId: String,
    ): FirestoreRecyclerOptions<ChatMsgModelResponse>

    fun getChat(): FirestoreRecyclerOptions<ChatroomModelResponse>

}