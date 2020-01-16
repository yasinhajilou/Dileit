package com.example.dileit.model.network;

import com.example.dileit.model.WordSearch;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {

    @GET("search")
    Call<WordSearch> SEARCH_CALL();
}
