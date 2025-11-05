package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.network.FirestoreFirebaseService
import com.azteca.chatapp.data.network.model.UserModel
import javax.inject.Inject

class SetDataUserUseCase @Inject constructor(private val firestoreService: FirestoreFirebaseService) {

    suspend fun setData(uuid: String, userModel: UserModel): Boolean {
        return try {
            return firestoreService.setInfUser(uuid, userModel)
        } catch (_: Exception) {
            false
        }
    }
}