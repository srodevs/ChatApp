package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.network.AuthFirebaseService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test


class GetUuidUseCaseTest {
    private val authFirebaseService: AuthFirebaseService = mockk(relaxed = true)
    private lateinit var getUuidUseCase: GetUuidUseCase

    @Before
    fun setUp() {
        getUuidUseCase = GetUuidUseCase(authFirebaseService)
    }

    @Test
    fun `when use case invoke, then return uuid`() {
        coEvery { authFirebaseService.getCurrentUid() } returns "123"

        val uuid = getUuidUseCase()

        assert(uuid?.isNotEmpty() == true)
        coVerify(exactly = 1) { authFirebaseService.getCurrentUid() }
    }

    @Test
    fun `when use case invoke, then return null`(){
        coEvery { authFirebaseService.getCurrentUid() } returns null

        val uuid = getUuidUseCase()

        assert(uuid == null)
        coVerify(exactly = 1) { authFirebaseService.getCurrentUid() }
    }

}