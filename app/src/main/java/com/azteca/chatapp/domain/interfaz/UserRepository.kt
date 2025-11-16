package com.azteca.chatapp.domain.interfaz

import android.net.Uri
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.domain.model.UserModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions

interface UserRepository {

    suspend fun getImageProfile(
        otherUserId: String,
    ): String

    suspend fun getOtherUserFromChat(
        uuid: String,
        listUser: List<String>,
    ): UserModelResponse?

    suspend fun getUserInf(): UserModelResponse?

    suspend fun updateUserInf(sendModel: UserModel, uriImage: Uri?): Boolean

    suspend fun searchUser(txtUsername: String): FirestoreRecyclerOptions<UserModelResponse>

}