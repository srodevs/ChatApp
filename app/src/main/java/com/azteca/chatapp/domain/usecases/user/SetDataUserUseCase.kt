package com.azteca.chatapp.domain.usecases.user

import com.azteca.chatapp.data.repository.UserRepositoryImpl
import com.azteca.chatapp.domain.model.UserModel
import javax.inject.Inject

class SetDataUserUseCase @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) {

    suspend fun setData(userModel: UserModel): Boolean {
        return try {
            return userRepositoryImpl.updateUserInf(userModel, null)
        } catch (_: Exception) {
            false
        }
    }
}