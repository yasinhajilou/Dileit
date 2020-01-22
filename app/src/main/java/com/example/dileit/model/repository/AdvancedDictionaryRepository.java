package com.example.dileit.model.repository;

import android.util.Log;

import com.example.dileit.model.ResponseObject;
import com.example.dileit.model.network.ApiCalls;
import com.example.dileit.model.network.RetrofitClientInstance;
import com.example.dileit.viewmodel.AdvancedDictionaryInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvancedDictionaryRepository {
    private ApiCalls mApiCalls;
    private String TAG = AdvancedDictionaryRepository.class.getSimpleName();
    private AdvancedDictionaryInterface mInterface;

    public AdvancedDictionaryRepository(AdvancedDictionaryInterface anInterface) {
        mApiCalls = RetrofitClientInstance.getRetrofit().create(ApiCalls.class);
        mInterface = anInterface;
    }

    public void searchForWord(String token, String query, String type, String filter) {
        mApiCalls.SEARCH_CALL(token, query, type, filter).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body().getListData().getWordSearches().size());
                        mInterface.onSuccessfully(response.body().getListData().getWordSearches());
                    } else
                        Log.d(TAG, "onResponse: response body is null");
                } else
                    Log.d(TAG, "onResponse: Response is not successful ");
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
