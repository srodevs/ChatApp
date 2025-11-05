package com.azteca.chatapp.ui.main.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.azteca.chatapp.common.SharedPrefs
import com.azteca.chatapp.data.network.model.UserModelResponse
import com.azteca.chatapp.databinding.FragmentSearchBinding
import com.azteca.chatapp.ui.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()
    private var adapter: SearchAdapter? = null
    private val searchUser = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String?): Boolean {
            if (!newText.isNullOrEmpty()) searchQuery(newText)
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrEmpty()) searchQuery(query)
            return true
        }
    }

    companion object {
        private const val TAG = "searchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            searchIvBack.setOnClickListener { parentFragmentManager.popBackStack() }
            searchSearchView.setOnQueryTextListener(searchUser)
        }
    }

    private fun searchQuery(txtUsername: String) {
        viewModel.querySearch(txtUsername) { opts ->
            adapter = SearchAdapter(
                uuid = SharedPrefs(requireContext()).getUuid(),
                options = opts,
                viewModel = viewModel,
                itemListener = { toChat(it) }
            )
            adapter!!.startListening()
        }

        binding.searchRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    private fun toChat(userModelResponse: UserModelResponse) {
        if (userModelResponse.userId != null) {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToChatFragment(
                    userModelResponse.userId!!,
                    userModelResponse.username,
                    userModelResponse.phone
                )
            )
        }
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) adapter!!.stopListening()
    }

    override fun onResume() {
        super.onResume()
        if (adapter != null) adapter!!.startListening()
    }
}