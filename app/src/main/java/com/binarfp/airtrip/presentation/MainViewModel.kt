package com.binarfp.airtrip.presentation

import androidx.lifecycle.*
import com.binarfp.airtrip.data.LocalRepository
import com.binarfp.airtrip.data.local.preference.DataStoreDataSource
import com.binarfp.airtrip.data.network.AirTripDataSource
import com.binarfp.airtrip.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val airTripDataSource: AirTripDataSource,private val dataStoreDataSource: DataStoreDataSource) :ViewModel() {
    val registResult : LiveData<Response<ResponseRegist>> get() = _registResult
    private val _registResult= MutableLiveData<Response<ResponseRegist>>()

    fun registrasi(
        requestBody: RequestBody
    ){
        viewModelScope.launch {
            val data = airTripDataSource.register(requestBody)
            _registResult.postValue(data)
//            Log.e("code",data.code().toString())
        }
    }

    val loginResult : LiveData<Response<ResponseLogin>> get() = _loginResult
    private val _loginResult = MutableLiveData<Response<ResponseLogin>>()

    fun login(
        requestBody: RequestBody
    ){
        viewModelScope.launch {
            val data = airTripDataSource.login(requestBody)
            _loginResult.postValue(data)
//            Log.e("code",data.code().toString())
        }
    }

    fun setAccessToken(token:String){
        viewModelScope.launch {
            dataStoreDataSource.setAccessToken(token)
        }
    }
    fun getAccesToken():LiveData<String>{
        return dataStoreDataSource.getAccessToken().asLiveData()
    }

    val getAirport : LiveData<List<DataAirport>> get() = _getAirport
    private val _getAirport = MutableLiveData<List<DataAirport>>()

    fun getAirports(){
        viewModelScope.launch {
            val data = airTripDataSource.getAirports()
            _getAirport.postValue(data.data)
//            Log.e("data",data.data.toString() )
        }
    }

    val getFlights : LiveData<List<Flight>> get() = _getFlights
    private val _getFlights = MutableLiveData<List<Flight>>()

    fun getFlights(){
        viewModelScope.launch {
            val data = airTripDataSource.getFlights()
            _getFlights.postValue(data.data)
//            Log.e("data",data.data.toString() )
        }
    }

    val ticket : LiveData<Ticket> get() =  _ticket
    private val _ticket = MutableLiveData<Ticket>()
    fun createTicket(token: String,requestBody: RequestBody){
        viewModelScope.launch {
            val data = airTripDataSource.createTicket(token,requestBody)
            _ticket.postValue(data.data)
        }
    }

    val history :LiveData<List<HistoryData>> get() = _history
    private val _history = MutableLiveData<List<HistoryData>>()
    fun getHistory(token: String){
        viewModelScope.launch {
            val data = airTripDataSource.getHistory(token)
            _history.postValue(data.data!!)
        }
    }

    suspend fun setRead(angka:Int){
        viewModelScope.launch {
            dataStoreDataSource.setRead(angka)
        }

    }
    fun getRead(): LiveData<Int> {
        return dataStoreDataSource.getRead().asLiveData()
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