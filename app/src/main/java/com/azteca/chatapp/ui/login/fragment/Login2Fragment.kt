package com.azteca.chatapp.ui.login.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.azteca.chatapp.R
import com.azteca.chatapp.common.xToast
import com.azteca.chatapp.databinding.FragmentLogin2Binding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "loginFragment2"

@AndroidEntryPoint
class Login2Fragment : Fragment() {
    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: Login2ViewModel by viewModels()
    private val args: Login2FragmentArgs by navArgs()
    private lateinit var txtNumber: String
    private var timerJob: Job? = null
    private var timerOut = 60L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** - - - - - - - normal  - - - - - - - - */
        /*txtNumber = args.number*/

        /** - - - - - - - - - - - - - - - - - -  */
        /** proof - start  */
        txtNumber = "+52 9999999922"
        /** proof - end  */
        /** - - - - - - - - - - - - - - - - - - */
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogin2Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initComponents()
        initListeners()
        initVM()
    }

    private fun initComponents() {
        startResendCodeTimer()
        sendCode()
    }

    private fun initVM() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect {
                    binding.login2Pg.isVisible = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.loginBtnSend.setOnClickListener {
            if (binding.loginBtnSend.text?.length == 6) {
                viewModel.verifyCode(binding.loginBtnSend.text.toString()) {
                    findNavController().navigate(
                        Login2FragmentDirections.actionLogin2FragmentToLogin3Fragment(txtNumber)
                    )
                }
            }
        }
        binding.loginTvResend.setOnClickListener { sendCode() }
    }

    @SuppressLint("SetTextI18n")
    private fun startResendCodeTimer() {
        timerJob?.cancel()
        timerJob = lifecycleScope.launch {
            binding.loginTvResend.isEnabled = false

            for (time in timerOut downTo 0) {
                binding.loginTvResend.text =
                    "${getText(R.string.login_resend_code)} $time"
                delay(1000)
            }
            timerOut = 60L
            binding.loginTvResend.isEnabled = true
            binding.loginTvResend.text = getText(R.string.login_resend_code)
        }
    }

    private fun sendCode() {
        viewModel.loginPhone(
            txtNumber,
            requireActivity(),
            onCodeSent = {
                Log.d(TAG, "code sent")
                requireContext().xToast("codigo enviado")

                /** - - - - - - - normal  - - - - - - - - */
                /*
                binding.loginEtNumber.requestFocus()
                val imm =
                    requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.loginEtNumber, InputMethodManager.SHOW_IMPLICIT)
                */
            },
            onVerificationCodeComplete = {
                //para pasar directo sin hacer click en comprobar code
                Log.d(TAG, "code completeVerification")
                requireContext().xToast("code sent y complete")
                findNavController().navigate(
                    Login2FragmentDirections.actionLogin2FragmentToLogin3Fragment(txtNumber)
                )
            },
            onVerificationFail = { fail ->
                Log.e(TAG, "auth error $fail")
                requireContext().xToast("auth error $fail")
            }
        )
    }

    override fun onStop() {
        super.onStop()
        timerJob?.cancel()
    }
}