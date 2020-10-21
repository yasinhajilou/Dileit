package com.yasinhajilou.dileit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yasinhajilou.dileit.model.Contributor
import com.yasinhajilou.dileit.model.repository.NetworkRepository
import kotlinx.coroutines.launch

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    private val liveMutableContributorList = MutableLiveData<List<Contributor>>()
    private val repo = NetworkRepository()
    val liveContributorList: LiveData<List<Contributor>> = liveMutableContributorList
    fun getContributors() {
        viewModelScope.launch {
            try {
                val list = repo.getContributors()
                liveMutableContributorList.postValue(list)
            } catch (e: Exception) {
                //mostly handled for network connections
            }

        }
    }
}