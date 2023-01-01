package com.binarfp.airtrip.presentation

import android.util.Log
import androidx.lifecycle.*
import com.binarfp.airtrip.data.LocalRepository
import com.binarfp.airtrip.model.*
import com.zaahid.challenge6.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val localRepository: LocalRepository) :ViewModel() {
    val registResult : LiveData<Resource<ResponseRegist>> get() = _registResult
    private val _registResult= MutableLiveData<Resource<ResponseRegist>>()

    fun registrasi(
        requestBody: RequestBody
    ){
        _registResult.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.registrasi(requestBody)
            viewModelScope.launch(Dispatchers.Main) { _registResult.postValue(data) }
//            Log.e("code",data.code().toString())
        }
    }

    val loginResult : LiveData<Resource<ResponseLogin>> get() = _loginResult
    private val _loginResult = MutableLiveData<Resource<ResponseLogin>>()

    fun login(
        requestBody: RequestBody
    ){
        _loginResult.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.login(requestBody)
            viewModelScope.launch(Dispatchers.Main) { _loginResult.postValue(data) }
//            Log.e("code",data.code().toString())
        }
    }

    val getAirports : LiveData<Resource<ResponseGetAirport>> get() = _getAirports
    private val _getAirports = MutableLiveData<Resource<ResponseGetAirport>>()

    fun getAirports(){
        _getAirports.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.getAirports()
            viewModelScope.launch(Dispatchers.Main) { _getAirports.postValue(data) }
//            Log.e("data",data.data.toString() )
        }
    }
    val getAirport : LiveData<DataAirport> get() = _getAirport
    private val _getAirport = MutableLiveData<DataAirport>()
    fun getAirportbyId(
        token: String,
        id: Int
    ){
        viewModelScope.launch {
            localRepository.getAirportbyId("Bearer $token",id)
        }
    }
    val getAirport2 : LiveData<DataAirport> get() = _getAirport2
    private val _getAirport2 = MutableLiveData<DataAirport>()
    fun getAirportbyId2(
        token: String,
        id: Int
    ){
        viewModelScope.launch {
            localRepository.getAirportbyId("Bearer $token",id)
        }
    }

    val getFlights : LiveData<Resource<ResponseFlight>> get() = _getFlights
    private val _getFlights = MutableLiveData<Resource<ResponseFlight>>()

    fun getFlights(){
        _getFlights.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.getFlights()
            viewModelScope.launch(Dispatchers.Main) { _getFlights.postValue(data) }
        }
    }

    val ticket : LiveData<Resource<responsTicket>> get() =  _ticket
    private val _ticket = MutableLiveData<Resource<responsTicket>>()
    fun createTicket(token: String,requestBody: RequestBody){
        _ticket.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.createTicket("Bearer $token",requestBody)
            viewModelScope.launch(Dispatchers.Main) { _ticket.postValue(data) }
        }
    }

    val history :LiveData<Resource<History>> get() = _history
    private val _history = MutableLiveData<Resource<History>>()
    fun getHistory(token: String){
        _history.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.getHistory("Bearer $token")
            viewModelScope.launch(Dispatchers.Main) { _history.postValue(data) }
        }
    }

    val responseUser : LiveData<Resource<ResponseUser>> get() = _responseUser
    private val _responseUser = MutableLiveData<Resource<ResponseUser>>()

    fun getProfile(token: String) {
        _responseUser.postValue(Resource.Loading())
        Log.e("getprofile", "getprofile")
        viewModelScope.launch {
            val data = localRepository.getProfile("Bearer $token")
            viewModelScope.launch(Dispatchers.Main) { _responseUser.postValue(data) }
        }
    }
    val responseUpdateUser : LiveData<Resource<ResponseUpdateUser>> get() = _responseUpdateUser
    private val _responseUpdateUser = MutableLiveData<Resource<ResponseUpdateUser>>()
    fun updateUser(token: String,id:Int,requestBody: RequestBody){
        viewModelScope.launch {
            val response = localRepository.updateUser("Bearer $token",id,requestBody)
            response.payload?.let {
                viewModelScope.launch(Dispatchers.Main) {_responseUpdateUser.postValue(response) }
            }
        }
    }

    val responsesNotif : LiveData<Resource<ResponsesNotif>> get() = _responseNotif
    private val _responseNotif = MutableLiveData<Resource<ResponsesNotif>>()
    fun getNotif(token: String){
        _responseNotif.postValue(Resource.Loading())
        viewModelScope.launch {
            val data = localRepository.getNotif("Bearer $token")
            viewModelScope.launch(Dispatchers.Main) {
                _responseNotif.postValue(data)
            }
        }
    }

    //from data Store
    fun setAccessToken(token:String){
        viewModelScope.launch {
            localRepository.setAccessToken(token)
        }
    }
    fun getAccesToken():LiveData<String>{
        return localRepository.getAccessToken().asLiveData()
    }

    fun setImageString(sImage:String){
        viewModelScope.launch {
            localRepository.setImageString(sImage)
        }
    }
    fun getImageString():LiveData<String>{
        return localRepository.getImageString().asLiveData()
    }



//    val listAirport : LiveData<List<DataAirport>> get() =  _listAirport
//    private val _listAirport = MutableLiveData<List<DataAirport>>()
//    fun setListAirport(list: List<DataAirport>){
//        _listAirport.postValue(list)
//    }
//    fun searchListWName(name : String,owner: LifecycleOwner){
//        listAirport.observe(owner, Observer {
//            val result = it.filter {
//                it.name == name
//            }
//            _listAirport.postValue(result)
//        })
//    }
}
