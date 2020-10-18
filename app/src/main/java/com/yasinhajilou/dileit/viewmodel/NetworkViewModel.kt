package com.yasinhajilou.dileit.viewmodel

import android.app.Application
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
            val list = repo.getContributors()
            liveMutableContributorList.postValue(list)
        }
    }
}