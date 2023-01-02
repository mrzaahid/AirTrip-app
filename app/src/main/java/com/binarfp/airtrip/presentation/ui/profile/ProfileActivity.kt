package com.binarfp.airtrip.presentation.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.binarfp.airtrip.R
import com.binarfp.airtrip.databinding.ActivityProfileBinding
import com.binarfp.airtrip.model.User
import com.binarfp.airtrip.presentation.MainViewModel
import com.binarfp.airtrip.presentation.Utils
import com.binarfp.airtrip.presentation.ui.admin.AdminActivity
import com.binarfp.airtrip.presentation.ui.auth.MainActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

const val REQUEST_CODE_PERMISSION = 201

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.getAccesToken().observe(this) {
            if (it.isEmpty()) {
                loginFirst()
            } else {
                mainViewModel.getProfile(it)
            }
        }
        mainViewModel.responseUpdateUser.observe(this){
            if (it is Resource.Success){
                mainViewModel.setAccessToken(it.payload!!.accessToken)
                val intent=Intent(this,PeralihanActivity::class.java)
                startActivity(intent)
            }
        }
        binding.btnLogout.setOnClickListener {
            mainViewModel.setAccessToken("")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.imageUpdate.setOnClickListener {
            checkingPermission()
            chooseImageDialog()
        }

        binding.imageTrash.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Hapus Gambar")
                .setPositiveButton("sure") { _, _ ->
                    gantiImageUser("delete")
                    binding.imageTrash.visibility = View.GONE
                }
                .setNegativeButton("Cancel") { _, _ -> }
                .show()
        }
        mainViewModel.responseUser.observe(this) {
            Log.e("ProfileActivity", "responseUser emitted a value")
            it.payload?.data?.let { user ->
                Log.e("ProfileActivity", "Updating image")
                setUserInfo(user)
                if (user.image.isNullOrEmpty()) {
                    // Set the image source to the default image if the image field is empty
                    binding.ivProfile.setImageResource(R.drawable.ic_account)
                } else {
                    // Use Glide to load the image from the URL if the image field is not empty
                    Glide.with(binding.ivProfile)
                        .load(user.image)
                        .apply(
                            RequestOptions()
                                .placeholder(R.drawable.ic_account)
                                .error(R.drawable.ic_account))
                        .into(binding.ivProfile)
                }
                if (user.role.name.lowercase() == "admin") {
                    binding.btnAdmin.visibility = View.VISIBLE
                }
                if (user.image.isNullOrEmpty().not()) {
                    binding.imageTrash.visibility = View.VISIBLE
                } else {
                    binding.imageTrash.visibility = View.GONE
                }
            }
        }
        binding.btnUpdate.setOnClickListener {
            if (checkForm()) {
                val jsonObject = JSONObject()
                jsonObject.put("email", binding.etEmail.text)
                if (binding.textinputpassword.text.toString().isEmpty()) {
                    jsonObject.put("password", "")
                } else {
                    jsonObject.put("password", binding.textinputpassword.text.toString())
                }
                jsonObject.put("image", "")
                jsonObject.put("phone", binding.etPhone)
                jsonObject.put("name", binding.etName.toString())
                jsonObject.put("address", binding.etEmail.toString())
                val jsonObjectString = jsonObject.toString()
                val requestBody =
                    jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
                mainViewModel.responseUser.observe(this) {
                    val user = it.payload?.data
                    if (user != null) {
                        setUserInfo(user)
                    }
                    user?.id?.let { it1 ->
                        mainViewModel.getAccesToken().observe(this) {
                            mainViewModel.updateUser("Bearer $it", it1, requestBody)
                        }
                    }
                }

            }

        }
        binding.cardToHome.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this,AdminActivity::class.java)
            startActivity(intent)
        }


    }


    private fun setUserInfo(user: User) {
        binding.etName.setText(user.name)
        binding.etEmail.setText(user.email)
        binding.etPhone.setText(user.phone.toString())
        binding.etAddress.setText(user.address)
        binding.tvSaldo.text = user.saldo.toString()
    }

    private fun gantiImageUser(sImage: String?) {
        mainViewModel.responseUser.observe(this) {it->
            val user = it.payload?.data
            val jsonObject = JSONObject()
            jsonObject.put("email", user?.email)
            jsonObject.put("password", "")
            jsonObject.put("image", sImage)
            jsonObject.put("phone", user?.phone)
            jsonObject.put("name", user?.name)
            jsonObject.put("address", user?.address)
            val jsonObjectString = jsonObject.toString()
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())
            user?.id?.let { it1 ->
                mainViewModel.getAccesToken().observe(this) {
                    mainViewModel.updateUser(it, it1, requestBody)
                }
            }
        }
    }

    private fun checkForm(): Boolean {
        var a = true
        if (Utils.isempty(binding.etName)) {
            Toast.makeText(this, "username is empty", Toast.LENGTH_SHORT).show()
            a = false
        }
        if (binding.etPhone.text.isEmpty()) {
            a = false
            Toast.makeText(this, "phone number is empty", Toast.LENGTH_SHORT).show()
        }
        if (Utils.isempty(binding.etEmail)) {
            a = false
            Toast.makeText(this, "email is empty", Toast.LENGTH_SHORT).show()
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text).matches()) {
            a = false
            Toast.makeText(this, "not email patter", Toast.LENGTH_SHORT).show()
        }
        if (Utils.isempty(binding.etAddress)) {
            a = false
            Toast.makeText(this, "address is empty", Toast.LENGTH_SHORT).show()
        }
        return a
    }

    private fun checkingPermission() {
        if (isGranted(
                this, Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), REQUEST_CODE_PERMISSION
            )
        ){}
    }

    private fun loginFirst() {
        AlertDialog.Builder(this)
            .setMessage("Login First!")
            .setPositiveButton("okey") { _, _ ->
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("galeri") { _, _ -> openGalery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private val cameraresult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        val stringImage = Utils.bitMapToString(bitmap)
        gantiImageUser(stringImage)
//        mainViewModel.setImageString(stringImage)
    }


    private fun openCamera() {
//        val photoFile = File.createTempFile("IMG_",".JPG",requireContext().getExternalFilesDir(
//            Environment.DIRECTORY_PICTURES))
//        val uri = FileProvider.getUriForFile(requireContext(), requireContext().packageName + ".provider",photoFile)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraresult.launch(cameraIntent)
    }

    private val galeryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
//            binding.imageProfile.setImageURI(result)
            var bitmapp = MediaStore.Images.Media.getBitmap(contentResolver, result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                result?.let {
                    val res = ImageDecoder.createSource(contentResolver, it)
                    bitmapp = ImageDecoder.decodeBitmap(res)
                }
            }
            gantiImageUser(Utils.bitMapToString(bitmapp))
//            mainViewModel.setImageString(Utils.bitMapToString(bitmapp))
        }

    private fun openGalery() {
        this.intent.type = "image/*"
        galeryResult.launch("image/*")
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(baseContext)
            .setTitle("Permission Denied")
            .setMessage("Permission is Denied, pleas Allow Permission from settings")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                val uri = Uri.fromParts("package",packageName,null)
//                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

}