package com.binarfp.airtrip.presentation.ui.buyer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.FragmentProfileBinding
import com.binarfp.airtrip.model.User

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
//    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var bitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        resultLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == Activity.RESULT_OK) {
//                    handleCameraImage(result.data)
//                }
//            }
//
//        showProfile()
//
//        viewModel.getUserId().observe(viewLifecycleOwner) {
//            viewModel.setUserId(it)
//        }
//
//        viewModel.message.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { message ->
//                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        binding.apply {
//            ivProfile.setOnClickListener {
//                openCamera()
//            }
//            btnUpdate.setOnClickListener {
//                updateProfile()
//            }
//
//        }
    }

//    private fun openCamera() {
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        resultLauncher.launch(cameraIntent)
//    }
//
//    private fun handleCameraImage(intent: Intent?) {
//        bitmap = intent?.extras?.get("data") as Bitmap
//        binding.ivProfile.setImageBitmap(bitmap)
//    }
//
//    private fun updateProfile() {
//        binding.apply {
//            viewModel.userData.observe(viewLifecycleOwner) {
//                val user = it
//                val updateUser = User(
//                    email = etEmail.text.toString(),
//                    password = etPassword.text.toString(),
//                    name = etName.text.toString(),
//                    phone = etPhone.text.toString(),
//                    address = etAddress.toString(),
//                    imageProfile = user.imageProfile
//                )
//                @Suppress("SENSELESS_COMPARISON")
//                if (bitmap != null) {
//                    updateUser.imageProfile = ImageHelper().convert(bitmap!!)
//                }
//                viewModel.update(updateUser)
//                reset()
//            }
//        }
//    }
//
//
//    private fun reset() {
//        binding.apply {
//            etName.clearFocus()
//            etAddress.clearFocus()
//        }
//    }
//
//    private fun showProfile() {
//        viewModel.userData.observe(viewLifecycleOwner) { user ->
//            binding.apply {
//                if (user.imageProfile != null) {
//                    ivProfile.setImageBitmap(ImageHelper().convert(user.imageProfile))
//                }
//                etName.setText(user.name)
//                etPhone.setText(user.phone)
//                etAddress.setText(user.address)
//                etEmail setText(user.email)
//                etPassword.setText(user.password)
//
//
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
}