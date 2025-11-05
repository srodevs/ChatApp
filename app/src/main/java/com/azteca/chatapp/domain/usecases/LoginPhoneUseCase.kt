package com.azteca.chatapp.domain.usecases

import android.app.Activity
import com.azteca.chatapp.data.network.AuthFirebaseService
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class LoginPhoneUseCase @Inject constructor(private val authFirebaseService: AuthFirebaseService) {

   operator fun invoke(
        string: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        authFirebaseService.loginPhone(string, activity, callback)
    }
}