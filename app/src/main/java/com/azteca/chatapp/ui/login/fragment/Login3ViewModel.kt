package com.azteca.chatapp.ui.login.fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azteca.chatapp.data.network.model.UserModel
import com.azteca.chatapp.domain.usecases.GetUuidUseCase
import com.azteca.chatapp.domain.usecases.SetDataUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Login3ViewModel @Inject constructor(
    private val getUuidUseCase: GetUuidUseCase,
    private val setDataUserUseCase: SetDataUserUseCase
) : ViewModel() {

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun validateInfUser(userModel: UserModel, res: (Boolean) -> Unit) {
        viewModelScope.launch {
            _loading.value = true

            val uuid = getUuidUseCase()
            Log.d(TAG, "current uuid -> $uuid")
            if (uuid != null) {
                userModel.apply { userId = uuid }
                val response = setDataUserUseCase.setData(uuid, userModel)
                Log.d(TAG, " set inf user -> $response")
                res(response)
            }
            _loading.value = false
        }
    }

    companion object {
        private const val TAG = "login3ViewModel"
    }
}