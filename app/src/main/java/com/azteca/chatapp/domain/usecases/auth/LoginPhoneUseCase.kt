package com.azteca.chatapp.domain.usecases.auth

import android.app.Activity
import com.azteca.chatapp.data.repository.AuthRepositoryImpl
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class LoginPhoneUseCase @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) {

    operator fun invoke(
        string: String,
        activity: Activity,
        callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) {
        authRepository.loginPhone(string, activity, callback)
    }
}