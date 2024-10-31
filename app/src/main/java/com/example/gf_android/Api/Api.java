package com.example.gf_android.Api;

import android.util.Log;

import com.example.gf_android.Api.Types.Tag;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {

    public static List<Tag> getTags() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        List<Tag> result = new ArrayList<>();
        Call<List<Tag>> call = apiService.getTags();
        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                if (response.isSuccessful()) {
                    List<Tag> tags = response.body();
                    result.addAll(tags);
                } else {
                    Log.e("MainActivity", "Request failed with status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {
                Log.e("MainActivity", "Error: " + t.getMessage());
            }
        });

        return result;
    }



}
