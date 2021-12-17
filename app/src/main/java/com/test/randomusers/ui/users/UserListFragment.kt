package com.test.randomusers.ui.users

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.test.randomusers.R
import com.test.randomusers.data.model.User
import com.test.randomusers.databinding.FragmentUserListBinding
import com.test.randomusers.utils.Utils.hasInternetConnection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

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
        fetchRandomUsers()
    }

    private fun initRecyclerview() {
        userListAdapter = UserListAdapter(::onUserClicked)
        binding.userRecyclerview.adapter = userListAdapter

        binding.userRecyclerview.adapter = userListAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter { userListAdapter.retry() },
            footer = LoaderStateAdapter { userListAdapter.retry() }
        )

        userListAdapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.userRecyclerview.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "Oops! ${it.error}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            userListAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.userRecyclerview.scrollToPosition(0) }
        }

    }

    private fun fetchRandomUsers() {
        if (hasInternetConnection(requireContext())) {
            lifecycleScope.launch {
                viewModel.fetchRandomUsers()?.collectLatest {
                    Log.e("tag", it.toString())
                    userListAdapter.submitData(it)
                }
            }
        } else {
            showNoInternet()
        }
    }

    private fun onUserClicked(user: User) {
        userListSaveState = binding.userRecyclerview.layoutManager?.onSaveInstanceState()
        findNavController().navigate(UserListFragmentDirections.toUserDetailsFragment(user))
    }

    private fun showNoInternet() {
        Snackbar.make(
            binding.constraint,
            getString(R.string.internet_connection_message), Snackbar.LENGTH_INDEFINITE
        )
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.color_accent))
            .setAction(getString(R.string.retry)) {
                fetchRandomUsers()
            }.show()
    }
}