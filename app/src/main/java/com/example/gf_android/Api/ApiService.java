package com.example.gf_android.Api;

import com.example.gf_android.Api.Types.Tag;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ApiService {
    @GET("tags")
    Call<List<Tag>> getTags();
}