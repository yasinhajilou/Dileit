package com.example.dileit.model.repository

import com.example.dileit.model.Contributor
import com.example.dileit.model.network.RetrofitInstance

class NetworkRepository {
    suspend fun getContributors(): List<Contributor> {
        return RetrofitInstance.githubAPI.callContributors()
    }
}