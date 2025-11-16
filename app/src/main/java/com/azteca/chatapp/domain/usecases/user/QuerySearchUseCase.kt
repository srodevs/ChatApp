package com.azteca.chatapp.domain.usecases.user

import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.data.repository.UserRepositoryImpl
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import javax.inject.Inject

class QuerySearchUseCase @Inject constructor(
    private val repository: UserRepositoryImpl
) {

    suspend operator fun invoke(query: String): FirestoreRecyclerOptions<UserModelResponse> =
        repository.searchUser(query)

}