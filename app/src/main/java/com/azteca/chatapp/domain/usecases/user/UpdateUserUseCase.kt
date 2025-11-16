package com.azteca.chatapp.domain.usecases.user

import android.net.Uri
import com.azteca.chatapp.data.repository.UserRepositoryImpl
import com.azteca.chatapp.domain.model.UserModel
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {

    suspend operator fun invoke(sendModel: UserModel, uriImage: Uri?): Boolean {
        return userRepository.updateUserInf(sendModel, uriImage)
    }

}