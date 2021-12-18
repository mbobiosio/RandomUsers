package com.test.randomusers.ui.users

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.test.randomusers.R
import com.test.randomusers.data.model.User
import com.test.randomusers.data.networkresource.NetworkStatus
import com.test.randomusers.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListAdapter: UserListAdapter
    private val viewModel: UserListViewModel by viewModels()
    private var userListSaveState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
        viewModel.fetchUsers()
        setObserver()
    }

    private fun initRecyclerview() {
        userListAdapter = UserListAdapter(::onUserClicked)
        binding.userRecyclerview.adapter = userListAdapter
    }

    private fun setObserver() {
        viewModel.userListLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkStatus.Loading -> {
                    showLoading()
                    if (it.data?.isEmpty()!!) {
                        showLoading()
                        showNoInternet()
                    } else {
                        hideLoading()
                        userListAdapter.submitList(it.data)
                    }
                }
                is NetworkStatus.Success -> {
                    hideLoading()
                    userListAdapter.submitList(it.data)
                }
                is NetworkStatus.Error -> {
                    hideLoading()
                    it.message?.let { errorMessage -> showSnackBar(errorMessage) }
                }
            }
        }
    }

    private fun onUserClicked(user: User) {
        userListSaveState = binding.userRecyclerview.layoutManager?.onSaveInstanceState()
        findNavController().navigate(UserListFragmentDirections.toUserDetailsFragment(user))
    }

    private fun showNoInternet() {
        showSnackBar(getString(R.string.internet_connection_message))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.constraint, message, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.color_accent))
            .setAction(getString(R.string.retry)) {
                viewModel.fetchUsers()
            }.show()
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }
}