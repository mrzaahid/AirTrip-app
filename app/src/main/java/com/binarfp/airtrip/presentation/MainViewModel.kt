package com.binarfp.airtrip.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binarfp.airtrip.data.LocalRepository
import com.binarfp.airtrip.model.ResponseLogin
import com.binarfp.airtrip.model.ResponseRegist
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val localRepository: LocalRepository) :ViewModel() {
    val registResult : LiveData<Response<ResponseRegist>> get() = _registResult
    private val _registResult= MutableLiveData<Response<ResponseRegist>>()

    val loginResult : LiveData<Response<ResponseLogin>> get() = _loginResult
    private val _loginResult = MutableLiveData<Response<ResponseLogin>>()

    fun registrasi(
        requestBody: RequestBody
    ){
        viewModelScope.launch {
            val data = localRepository.registrasi(requestBody)
            _registResult.postValue(data)
            Log.e("code",data.code().toString())
        }
    }
    fun login(
        requestBody: RequestBody
    ){
        viewModelScope.launch {
            val data = localRepository.login(requestBody)
            _loginResult.postValue(data)
            Log.e("email",data.body()!!.accessToken)
            Log.e("code",data.code().toString())
        }
    }
}