package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.repository.AuthRepositoryImpl
import com.azteca.chatapp.domain.usecases.auth.VerifyCodeUseCode
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class VerifyCodeUseCodeTest {
    private val authFirebaseService: AuthRepositoryImpl = mockk(relaxed = true)
    private lateinit var verifyCodeUseCode: VerifyCodeUseCode

    @Before
    fun onBefore() {
        verifyCodeUseCode = VerifyCodeUseCode(authFirebaseService)
    }

    @Test
    fun `given verifyId and code, when use case is invoked, then verify code is invoked`() =
        runTest {
            // Given
            val verifyId = "verifyId"
            val code = "code"

            // When
            verifyCodeUseCode(verifyId, code)

            // Then
            coVerify(exactly = 1) { authFirebaseService.verifyCode(verifyId, code) }
        }
}