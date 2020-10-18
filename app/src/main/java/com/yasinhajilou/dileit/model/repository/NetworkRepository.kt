package com.yasinhajilou.dileit.model.repository

import com.yasinhajilou.dileit.model.Contributor
import com.yasinhajilou.dileit.model.network.RetrofitInstance

class NetworkRepository {
    suspend fun getContributors(): List<Contributor> {
        return RetrofitInstance.githubAPI.callContributors()
    }
}