package com.azteca.chatapp.domain.usecases

import com.azteca.chatapp.data.network.FirestoreFirebaseService
import com.azteca.chatapp.data.network.model.UserModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.bouncycastle.util.test.SimpleTest.runTest
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import java.sql.Timestamp
import kotlin.String


class SetDataUserUseCaseTest {
    private val firestoreFirebaseService: FirestoreFirebaseService = mockk(relaxed = true)
    private lateinit var setDataUserUseCase: SetDataUserUseCase

    @Before
    fun setUp() {
        setDataUserUseCase = SetDataUserUseCase(firestoreFirebaseService)
    }

    @Test
    fun `given uuid and user model, when set data, then return true`() = runTest {
        val uuid = "123"
        val userModel = getUserModel()
        coEvery { firestoreFirebaseService.setInfUser(uuid, userModel) } returns true

        val result = setDataUserUseCase.setData(uuid, userModel)

        assertEquals(true, result)
        coVerify(exactly = 1) { firestoreFirebaseService.setInfUser(uuid, userModel) }
    }

    @Test
    fun `given uuid and user model, when set data, then return false`() = runTest {
        val uuid = "123"
        val userModel = getUserModel()
        coEvery { firestoreFirebaseService.setInfUser(uuid, userModel) } returns false

        val result = setDataUserUseCase.setData(uuid, userModel)

        assertEquals(false, result)
        coVerify(exactly = 1) { firestoreFirebaseService.setInfUser(uuid, userModel) }
    }

    @Test
    fun `given uuid and user model, when set data, then return Exception`() = runTest {
        val uuid = "123"
        val userModel = getUserModel()
        coEvery { firestoreFirebaseService.setInfUser(uuid, userModel) }  throws Exception("Firestore error")

        val result = setDataUserUseCase.setData(uuid, userModel)

        assertFalse(result)
        coVerify(exactly = 1) { firestoreFirebaseService.setInfUser(uuid, userModel) }
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