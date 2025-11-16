package com.azteca.chatapp.data.repository

import com.azteca.chatapp.data.network.model.ChatMsgModelResponse
import com.azteca.chatapp.data.network.model.ChatroomModelResponse
import com.azteca.chatapp.data.network.service.AuthFirebaseService
import com.azteca.chatapp.data.network.service.FirestoreFirebaseService
import com.azteca.chatapp.data.network.service.FirestoreFirebaseService.Companion.DB_TIMESTAMP
import com.azteca.chatapp.domain.interfaz.ChatRoomRepository
import com.azteca.chatapp.domain.model.ChatMsgModel
import com.azteca.chatapp.domain.model.ChatroomModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val firestore: FirestoreFirebaseService,
    private val authFirebaseService: AuthFirebaseService
) : ChatRoomRepository {

    override fun getChatRoomId(
        otherUserId: String,
    ): String =
        firestore.getChatroomId(
            authFirebaseService.getCurrentUid().toString(), otherUserId
        )

    override suspend fun getChatroom(
        chatroomId: String,
        otherUserId: String,
    ): Pair<Boolean, ChatroomModelResponse?> {
        var success = false
        var res: ChatroomModelResponse? = null
        firestore.getChatroom(chatroomId).get().addOnCompleteListener {
            if (it.isSuccessful) {
                if (it.result.exists()) {
                    success = true
                    res = it.result.toObject(ChatroomModelResponse::class.java)
                } else {
                    success = false
                    res = null
                }
            }
        }
        return Pair(success, res)
    }

    override fun setChatroom(
        chatroomId: String,
        chatSend: ChatroomModel,
    ) {
        firestore.getChatroom(chatroomId).set(chatSend)
    }

    override fun getChatRoomMsg(
        chatroomId: String,
        chatMsgModel: ChatMsgModel,
    ): Boolean {
        var res = false
        chatMsgModel.senderId = authFirebaseService.getCurrentUid().toString()
        firestore.getChatroomMsg(chatroomId).add(chatMsgModel).addOnCompleteListener {
            res = it.isSuccessful
        }
        return res
    }

    override fun getChatRoomMsgForAdapter(
        chatroomId: String,
    ): FirestoreRecyclerOptions<ChatMsgModelResponse> {
        val query = firestore.getChatroomMsg(chatroomId).orderBy(
            FirestoreFirebaseService.DB_TIMESTAMP,
            Query.Direction.DESCENDING
        )

        return FirestoreRecyclerOptions
            .Builder<ChatMsgModelResponse>()
            .setQuery(query, ChatMsgModelResponse::class.java)
            .build()
    }

    override fun getChat(): FirestoreRecyclerOptions<ChatroomModelResponse> {
        val resUuid = authFirebaseService.getCurrentUid().orEmpty()
        val query = firestore.getChatroomCollections()
            .whereArrayContains(FirestoreFirebaseService.DB_LIST_USER, resUuid)
            .orderBy(DB_TIMESTAMP, Query.Direction.DESCENDING)

        return FirestoreRecyclerOptions
            .Builder<ChatroomModelResponse>()
            .setQuery(query, ChatroomModelResponse::class.java)
            .build()
    }
}