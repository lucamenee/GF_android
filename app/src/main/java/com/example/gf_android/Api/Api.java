package com.example.gf_android.Api;

import android.util.Log;

import com.example.gf_android.Api.Types.Alimenti;
import com.example.gf_android.Api.Types.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {

    private static final ApiService apiService;

    static {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    private static <T> List<T> getApiResponse(Call<List<T>> call) {
        List<T> result = new ArrayList<>();
        call.enqueue(new Callback<List<T>>() {
            @Override
            public void onResponse(Call<List<T>> call, Response<List<T>> response) {
                if (response.isSuccessful()) {
                    List<T> data = response.body();
                    result.addAll(data);
                } else {
                    Log.e("Api", "Request failed with status: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<T>> call, Throwable t) {
                Log.e("Api", "Error: " + t.getMessage());
            }
        });

        return result;
    }

    public static List<Tag> getTags() {
        Call<List<Tag>> call = apiService.getTags();
        return getApiResponse(call);
    }

    public static List<Alimenti> getAlimenti() {
        Call<List<Alimenti>> call = apiService.getAlimenti();
        return getApiResponse(call);
    }


}
