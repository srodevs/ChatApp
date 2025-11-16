package com.azteca.chatapp.ui.main.fragment.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.domain.model.UserModel
import com.azteca.chatapp.domain.usecases.auth.LogOutUseCase
import com.azteca.chatapp.domain.usecases.user.GetImgUserCase
import com.azteca.chatapp.domain.usecases.user.GetUserInfUseCase
import com.azteca.chatapp.domain.usecases.user.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val getUserUseCase: GetUserInfUseCase,
    private val getImgProfileUseCase: GetImgUserCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {

    fun getUser(response: (UserModelResponse?) -> Unit, urlImage: (String) -> Unit) {
        viewModelScope.launch {
            val userRes = getUserUseCase.invoke()
            response(userRes)
            if (userRes != null) {
                urlImage(getImgProfileUseCase.invoke(userRes.userId.orEmpty()))
            }
        }
    }

    fun updateUser(sendModel: UserModel, uriImage: Uri?, resUpdate: (Boolean) -> Unit) {
        viewModelScope.launch {
            resUpdate(updateUserUseCase.invoke(sendModel, uriImage))
        }
    }

    fun logOut(response: () -> Unit) {
        viewModelScope.launch {
            logOutUseCase.invoke()
            response()
        }
    }
}