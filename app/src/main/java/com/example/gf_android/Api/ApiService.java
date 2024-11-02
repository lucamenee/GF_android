package com.example.gf_android.Api;

import com.example.gf_android.Api.Types.Alimenti;
import com.example.gf_android.Api.Types.LoginResponse;
import com.example.gf_android.Api.Types.ResponseMsg;
import com.example.gf_android.Api.Types.Tag;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("tags")
    Call<List<Tag>> getTags();

    @GET("alimenti")
    Call<List<Alimenti>> getAlimenti();

    @POST("login")
    Call<LoginResponse> login(@Query("username") String username, @Query ("password") String password);

    @POST("register")
    Call<ResponseMsg> register(@Query ("username") String username, @Query ("password") String password,
                               @Query ("email") String email, @Query ("kcal") int kcal);

}