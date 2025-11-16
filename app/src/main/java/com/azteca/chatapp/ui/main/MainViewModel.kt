package com.azteca.chatapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azteca.chatapp.common.SharedPrefs
import com.azteca.chatapp.data.network.service.AuthFirebaseService
import com.azteca.chatapp.data.network.service.FirebaseMessageService
import com.azteca.chatapp.data.network.service.FirestoreFirebaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseMessageService: FirebaseMessageService,
    private val firestore: FirestoreFirebaseService,
    private val authFirebaseService: AuthFirebaseService,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    fun isUserLogged(response: (Boolean) -> Unit) {
        response(sharedPrefs.getValueLogin())
    }

    fun getFcm(response: (String) -> Unit) {
        viewModelScope.launch {
            val resToken = firebaseMessageService.getFcmDevice()
            if (resToken.isNotEmpty()) {
                sharedPrefs.setValueLogin(true)
                val res = authFirebaseService.getCurrentUid().toString()
                sharedPrefs.setUuid(res)
                val uuid = authFirebaseService.getCurrentUid()
                firestore.getInfUser(uuid.toString()).update("fcmToken", resToken)
                response(resToken)
            }
        }
    }

}