package com.binarfp.airtrip.presentation

import android.util.Log
import androidx.lifecycle.*
import com.binarfp.airtrip.data.LocalRepository
import com.binarfp.airtrip.model.DataAirport
import com.binarfp.airtrip.model.ResponseGetAirport
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

    val getAirport : LiveData<List<DataAirport>> get() = _getAirport
    private val _getAirport = MutableLiveData<List<DataAirport>>()

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
            Log.e("code",data.code().toString())
        }
    }

    fun setAccessToken(token:String){
        viewModelScope.launch {
            localRepository.setAccessToken(token)
        }
    }
    fun getAccesToken():LiveData<String>{
        return localRepository.getAccessToken().asLiveData()
    }
    fun getAirports(){
        viewModelScope.launch {
            val data = localRepository.getAirports()
            _getAirport.postValue(data.data)
            Log.e("data",data.data.toString() )
        }
    }

}