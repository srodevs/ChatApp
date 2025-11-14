package com.azteca.chatapp.domain.usecases

import android.app.Activity
import com.azteca.chatapp.data.network.AuthFirebaseService
import com.google.firebase.auth.PhoneAuthProvider
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LoginPhoneUseCaseTest {
    private val authFirebaseService: AuthFirebaseService = mockk(relaxed = true)
    private lateinit var useCase: LoginPhoneUseCase

    @Before
    fun setup() {
        useCase = LoginPhoneUseCase(authFirebaseService)
    }

    @Test
    fun `when use case is invoke, then call authFirebaseService loginPhone`() {
        // Given
        val phone = "+529991234567"
        val activity = Robolectric.buildActivity(Activity::class.java).get()
        val callback = mockk<PhoneAuthProvider.OnVerificationStateChangedCallbacks>(relaxed = true)

        // When
        useCase(phone, activity, callback)

        // Then
        coVerify(exactly = 1) {
            authFirebaseService.loginPhone(phone, any(), callback)
        }
    }
}