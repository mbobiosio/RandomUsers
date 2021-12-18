package com.test.randomusers.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.test.randomusers.R
import com.test.randomusers.data.model.User
import com.test.randomusers.databinding.FragmentUserDetailsBinding
import com.test.randomusers.utils.Utils.hasInternetConnection
import com.test.randomusers.utils.glide.GlideApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding
    private var user: User? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        setUpView()
    }

    private fun getArgs() {
        val data: UserDetailsFragmentArgs by navArgs()
        user = data.user
    }

    private fun setUpView() {
        with(binding) {
            // load the image
            if (hasInternetConnection(requireContext())) {
                GlideApp.with(requireContext()).load(user?.picture?.large)
                    .circleCrop()
                    .into(userAvatar)
            } else showSnackBar(getString(R.string.user_image_no_internet_message))

            userFullName.text = user?.fullNameWithTitle
            userGenderAndAge.text = user?.genderAndAge
            userUsername.text = user?.login?.username
            userEmail.text = user?.email
            userPhone.text = user?.phone
            userLocation.text = user?.location?.fullLocation
            userSince.text = user?.registered?.userSince
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.constraint, message, Snackbar.LENGTH_LONG)
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.color_accent))
            .show()
    }
}