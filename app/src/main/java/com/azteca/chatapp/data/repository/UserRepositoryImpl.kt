package com.azteca.chatapp.data.repository

import android.net.Uri
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.data.network.service.AuthFirebaseService
import com.azteca.chatapp.data.network.service.FirestoreFirebaseService
import com.azteca.chatapp.domain.interfaz.UserRepository
import com.azteca.chatapp.domain.model.UserModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirestoreFirebaseService,
    private val authFirebaseService: AuthFirebaseService
) : UserRepository {

    override suspend fun getImageProfile(otherUserId: String): String {
        var img = ""
        firestore.refImgProfileUser(otherUserId).downloadUrl.addOnCompleteListener { ref ->
            img = ref.result.toString()
        }
        return img
    }

    override suspend fun getOtherUserFromChat(
        uuid: String,
        listUser: List<String>
    ): UserModelResponse? {
        var userModelResponse: UserModelResponse? = null
        firestore.getOtherUserFromChatRoom(listUser, uuid).get().addOnCompleteListener {
            if (it.isSuccessful) {
                userModelResponse = it.result.toObject(UserModelResponse::class.java)
            }
        }
        return userModelResponse
    }

    override suspend fun getUserInf(): UserModelResponse? {
        val uuid = authFirebaseService.getCurrentUid().orEmpty()
        var model: UserModelResponse? = null
        firestore.getInfUser(uuid).get().addOnCompleteListener { res ->
            model = res.result.toObject(UserModelResponse::class.java)
        }
        return model
    }

    override suspend fun updateUserInf(sendModel: UserModel, uriImage: Uri?): Boolean {
        var result = false
        val resUuid = authFirebaseService.getCurrentUid().orEmpty()
        firestore.getInfUser(resUuid).set(sendModel).addOnCompleteListener {
            if (it.isSuccessful) {
                if (uriImage != null) {
                    firestore.refImgProfileUser(resUuid).putFile(uriImage)
                        .addOnCompleteListener { resImg ->
                            result = resImg.isSuccessful
                        }
                } else {
                    result = true
                }
            }
        }
        return result
    }

    override suspend fun searchUser(txtUsername: String): FirestoreRecyclerOptions<UserModelResponse> {
        val query = firestore.collectionUser()
            .whereGreaterThanOrEqualTo(FirestoreFirebaseService.DB_USERNAME, txtUsername)
            .whereLessThanOrEqualTo(
                FirestoreFirebaseService.DB_USERNAME,
                txtUsername + '\uf8ff'
            )

        return FirestoreRecyclerOptions.Builder<UserModelResponse>()
            .setQuery(query, UserModelResponse::class.java)
            .build()
    }

}