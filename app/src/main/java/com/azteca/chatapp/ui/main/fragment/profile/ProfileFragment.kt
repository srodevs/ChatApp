package com.azteca.chatapp.ui.main.fragment.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.azteca.chatapp.R
import com.azteca.chatapp.common.SharedPrefs
import com.azteca.chatapp.common.xLoadImg
import com.azteca.chatapp.common.xToast
import com.azteca.chatapp.data.network.model.UserModel
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.databinding.FragmentProfileBinding
import com.azteca.chatapp.ui.login.LoginActivity
import com.azteca.chatapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private var userModel: UserModelResponse? = null
    private var uriImage: Uri? = null
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                uriImage = it.data?.data
                binding.profileIvUser.xLoadImg(uriImage.toString())
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
    }

    private fun initComponent() {
        with(binding) {
            profileIvBack.setOnClickListener { parentFragmentManager.popBackStack() }
            updateBtnSend.setOnClickListener { validateData() }
            profileTvLogout.setOnClickListener { userLogout() }
            profileIvUser.setOnClickListener { imgeUser() }
        }
        getProfileUser()
    }

    private fun getProfileUser() {
        viewModel.getUser(
            response = { resUser ->
                userModel = resUser
                if (userModel != null) {
                    binding.profileEtUsername.setText(userModel!!.username)
                    binding.profileEtNumber.setText(userModel!!.phone)
                }
            },
            urlImage = { url ->
                binding.profileIvUser.xLoadImg(url)
            }
        )
    }

    private fun validateData() {
        val newUsername = binding.profileEtUsername.text.toString().trim()
        if (newUsername.isNotEmpty()
            && userModel != null
        ) {
            binding.profilePg.isVisible = true
            val sendModel = UserModel(
                userModel!!.userId,
                userModel!!.phone,
                newUsername,
                Timestamp(System.currentTimeMillis()),
                MainActivity.txtFmcToken
            )
            viewModel.updateUser(sendModel, uriImage) {
                if (it) {
                    requireContext().xToast(getString(R.string.profile_data_updated))
                    parentFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun imgeUser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private fun userLogout() {
        viewModel.logOut {
            Log.d("perfil", "cerrar sesion")
            requireActivity().finish()
            SharedPrefs(requireContext()).setValueLogin(false)
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
}