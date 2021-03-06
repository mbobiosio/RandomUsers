package com.test.randomusers.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.test.randomusers.data.model.User
import com.test.randomusers.databinding.UserListItemBinding
import com.test.randomusers.utils.glide.GlideApp

class UserListAdapter(val onUserClicked: (user: User) -> Unit) : ListAdapter<User, UserListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: UserListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bind(userData: User?) {
            userData?.let { user ->
                with(binding) {
                    // load the image
                    GlideApp.with(context).load(userData.picture?.medium)
                        .transform(CenterInside(), RoundedCorners(100))
                        .into(userAvatar)

                    // set the name
                    userFullName.text = userData.fullName

                    // set the location
                    userLocation.text = userData.shortLocation
                }
                binding.root.setOnClickListener { onUserClicked(user) }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}