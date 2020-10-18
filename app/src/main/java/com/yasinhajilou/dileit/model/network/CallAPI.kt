package com.yasinhajilou.dileit.model.network

import com.yasinhajilou.dileit.model.Contributor
import retrofit2.http.GET
import retrofit2.http.Path

interface CallAPI {
    @GET("repos/{user}/{repo}/contributors")
    suspend fun callContributors(@Path("user") user: String = "yasinhajiloo",
                                 @Path("repo") repo: String = "Dileit"): List<Contributor>
}