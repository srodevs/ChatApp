package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.repository.AuthRepositoryImpl
import com.azteca.chatapp.domain.usecases.auth.GetUuidUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test


class GetUuidUseCaseTest {
    private val authFirebaseService: AuthRepositoryImpl = mockk(relaxed = true)
    private lateinit var getUuidUseCase: GetUuidUseCase

    @Before
    fun setUp() {
        getUuidUseCase = GetUuidUseCase(authFirebaseService)
    }

    @Test
    fun `when use case invoke, then return uuid`() {
        coEvery { authFirebaseService.getUuid() } returns "123"

        val uuid = getUuidUseCase()

        assert(uuid?.isNotEmpty() == true)
        coVerify(exactly = 1) { authFirebaseService.getUuid() }
    }

}