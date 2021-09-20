package com.vaishnav.flobizassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaishnav.flobizassignment.api.models.DataResponseItem
import com.vaishnav.flobizassignment.repository.MainActivityRepository
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val _data = MutableLiveData<List<DataResponseItem>>()
    val data: LiveData<List<DataResponseItem>> = _data

    fun getMyData() = viewModelScope.launch {
        MainActivityRepository.getMyData()?.let {
            _data.postValue(it)
        }
    }
}