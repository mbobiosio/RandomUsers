package com.test.randomusers.ui.users

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.test.randomusers.data.model.User
import com.test.randomusers.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userListAdapter: UserListAdapter
    private var userListSaveState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()
    }

    private fun initRecyclerview() {
        userListAdapter = UserListAdapter(::onUserClicked)
        binding.userRecyclerview.adapter = userListAdapter

//        binding.userRecyclerview.adapter = userListAdapter.withLoadStateHeaderAndFooter(
//            header = "",
//            footer = ""
//        )

        userListAdapter.addLoadStateListener { loadState ->

        }
    }

    private fun onUserClicked(user: User) {
        userListSaveState = binding.userRecyclerview.layoutManager?.onSaveInstanceState()
        findNavController().navigate(UserListFragmentDirections.toUserDetailsFragment(user))
    }
}