package com.azteca.chatapp.ui.main.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.domain.usecases.user.GetImgUserCase
import com.azteca.chatapp.domain.usecases.user.QuerySearchUseCase
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getImg: GetImgUserCase,
    private val searchUser: QuerySearchUseCase
) : ViewModel() {

    fun querySearch(
        txtUsername: String,
        response: (FirestoreRecyclerOptions<UserModelResponse>) -> Unit
    ) {
        viewModelScope.launch {
            val res: FirestoreRecyclerOptions<UserModelResponse> = searchUser.invoke(txtUsername)
            response(res)
        }
    }

    fun getImg(s: String, function: (String) -> Unit) {
        viewModelScope.launch {
            function(getImg.invoke(s))
        }
    }

}