package com.example.gf_android.Api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gf_android.Api.Types.Alimenti;
import com.example.gf_android.Api.Types.LoginResponse;
import com.example.gf_android.Api.Types.Tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {

    private static final ApiService apiService;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);


    static {
        apiService = RetrofitClient.getClient().create(ApiService.class);
    }

    private static <T> T getApiResponse(Call<T> call) {
        Future<T> future = executorService.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    Response<T> response = call.execute();
                    if (response.isSuccessful()) {
                        return response.body();
                    } else {
                        Log.e("Api", "Request failed with status: " + response.code());
                    }
                } catch (IOException e) {
                    Log.e("Api", "Error: " + e.getMessage());
                }
                return null;
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("Api", "Error: " + e.getMessage());
        }
        return null;
    }

    public static List<Tag> getTags() {
        Call<List<Tag>> call = apiService.getTags();
        return getApiResponse(call);
    }

    public static List<Alimenti> getAlimenti() {
        Call<List<Alimenti>> call = apiService.getAlimenti();
        return getApiResponse(call);
    }

    public static LoginResponse login(String username, String password) {
        Call<LoginResponse> call = apiService.login(username, password);
        return getApiResponse(call);
    }


}
