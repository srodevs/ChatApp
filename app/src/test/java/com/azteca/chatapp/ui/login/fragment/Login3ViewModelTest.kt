package com.azteca.chatapp.ui.login.fragment

import com.azteca.chatapp.domain.model.UserModel
import com.azteca.chatapp.domain.usecases.auth.GetUuidUseCase
import com.azteca.chatapp.domain.usecases.user.SetDataUserUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Login3ViewModelTest {
    @MockK
    private lateinit var getUuidUseCase: GetUuidUseCase

    @MockK
    private lateinit var setDataUserUseCase: SetDataUserUseCase

    private lateinit var viewModel: Login3ViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        MockKAnnotations.init(this)
        viewModel = Login3ViewModel(getUuidUseCase, setDataUserUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `validateInfUser should assign uuid, call setData and update loading`() = runTest {
        // given
        val uuid = "123-ABC"
        val userModel = UserModel(
            userId = "",
            phone = "998811223344",
            timestamp = null,
            username = "Lopez",
            fcmToken = "",
        )
        coEvery { getUuidUseCase.invoke() } returns uuid
        coEvery { setDataUserUseCase.setData(any()) } returns true

        var callbackResult: Boolean? = null

        // when
        viewModel.validateInfUser(userModel) {
            callbackResult = it
        }

        // Assert
        coVerify(exactly = 1) { getUuidUseCase.invoke() }
        coVerify(exactly = 1) { setDataUserUseCase.setData(userModel) }
        assertEquals(callbackResult, true)
        assertEquals(viewModel.loading.value, false)
    }

}