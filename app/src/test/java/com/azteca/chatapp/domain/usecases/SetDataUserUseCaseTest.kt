package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.repository.UserRepositoryImpl
import com.azteca.chatapp.domain.model.UserModel
import com.azteca.chatapp.domain.usecases.user.SetDataUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test


class SetDataUserUseCaseTest {
    private val firestoreFirebaseService: UserRepositoryImpl = mockk(relaxed = true)
    private lateinit var setDataUserUseCase: SetDataUserUseCase

    @Before
    fun setUp() {
        setDataUserUseCase = SetDataUserUseCase(firestoreFirebaseService)
    }

    @Test
    fun `given uuid and user model, when set data, then return true`() = runTest {
        val uuid = "123"
        val userModel = getUserModel()
        coEvery { firestoreFirebaseService.updateUserInf(userModel, null) } returns true

        val result = setDataUserUseCase.setData(userModel)

        assertEquals(true, result)
        coVerify(exactly = 1) { firestoreFirebaseService.updateUserInf(userModel, null) }
    }

    @Test
    fun `given uuid and user model, when set data, then return false`() = runTest {
        val uuid = "123"
        val userModel = getUserModel()
        coEvery {
            firestoreFirebaseService.updateUserInf(
                sendModel = userModel,
                null
            )
        } returns false

        val result = setDataUserUseCase.setData(userModel)

        assertEquals(false, result)
        coVerify(exactly = 1) {
            firestoreFirebaseService.updateUserInf(
                sendModel = userModel,
                null
            )
        }
    }

    @Test
    fun `given uuid and user model, when set data, then return Exception`() = runTest {
        val uuid = "123"
        val userModel = getUserModel()
        coEvery {
            firestoreFirebaseService.updateUserInf(
                sendModel = userModel,
                null
            )
        } throws Exception("Firestore error")

        val result = setDataUserUseCase.setData(userModel)

        assertFalse(result)
        coVerify(exactly = 1) {
            firestoreFirebaseService.updateUserInf(
                sendModel = userModel,
                null
            )
        }
    }

    private fun getUserModel(): UserModel {
        return UserModel(
            userId = "Android",
            phone = "5544332211",
            username = "androiDEv",
            timestamp = null,
            fcmToken = "",
        )
    }
}