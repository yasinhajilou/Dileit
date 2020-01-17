package com.example.dileit.model.repository;

import android.util.Log;
import android.widget.Toast;

import com.example.dileit.model.SearchResponse;
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
        mApiCalls.SEARCH_CALL(token, query, type, filter).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mInterface.onSuccessfully(response.body().getListData().getWordSearches());
                    } else
                        Log.d(TAG, "onResponse: response body is null");
                } else
                    Log.d(TAG, "onResponse: Response is not successful ");
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
