package com.azteca.chatapp.domain.usecases.user

import com.azteca.chatapp.data.repository.UserRepositoryImpl
import javax.inject.Inject

class GetImgUserCase @Inject constructor(
    private val userRepo: UserRepositoryImpl
) {

    suspend operator fun invoke(id: String) = userRepo.getImageProfile(id)

}