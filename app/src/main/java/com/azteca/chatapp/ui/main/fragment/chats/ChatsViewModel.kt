package com.azteca.chatapp.ui.main.fragment.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azteca.chatapp.data.network.model.ChatroomModelResponse
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.domain.usecases.chatroom.GetChatUseCase
import com.azteca.chatapp.domain.usecases.chatroom.GetOtherUserFromChatRoomUseCase
import com.azteca.chatapp.domain.usecases.user.GetImgUserCase
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getChats: GetChatUseCase,
    private val getOtherUserFromChatRoom: GetOtherUserFromChatRoomUseCase,
    private val getImgProfileUser: GetImgUserCase,
) : ViewModel() {

    fun getChats(opt: (FirestoreRecyclerOptions<ChatroomModelResponse>) -> Unit) {
        viewModelScope.launch {
            opt(getChats())
        }
    }

    fun getOtherUserFromChatRoom(
        uuid: String,
        listUser: List<String>,
        responseUser: (UserModelResponse?) -> Unit,
        responseImg: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            val result: UserModelResponse? = getOtherUserFromChatRoom.invoke(uuid, listUser)
            responseUser(result)
            if (result != null) {
                responseImg(getImgProfileUser.invoke(result.userId.orEmpty()))
            }
        }
    }
}