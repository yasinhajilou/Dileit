package com.example.dileit.model.network;

import com.example.dileit.model.ResponseObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCalls {

    @GET("search")
    Call<ResponseObject> SEARCH_CALL(@Query("token") String token, @Query("q") String query,
                                     @Query("type") String type, @Query("filter") String filter);


}
