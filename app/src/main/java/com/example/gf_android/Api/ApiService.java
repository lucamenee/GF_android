package com.example.gf_android.Api;

import com.example.gf_android.Api.Types.Alimento;
import com.example.gf_android.Api.Types.LoginResponse;
import com.example.gf_android.Api.Types.ResponseMsg;
import com.example.gf_android.Api.Types.Tag;
import com.example.gf_android.Api.Types.UpdateInsertMsg;
import com.example.gf_android.Api.Types.Utente;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface ApiService {
    @GET("tags")
    Call<List<Tag>> getTags();

    @GET("alimenti")
    Call<List<Alimento>> getAlimenti();

    @POST("login")
    Call<LoginResponse> login(@Query("username") String username, @Query ("password") String password);

    @POST("register")
    Call<ResponseMsg> register(@Query ("username") String username, @Query ("password") String password,
                               @Query ("email") String email, @Query ("kcal") int kcal);

    @GET("inventory")
    Call<List<Alimento>> inventory(@Query("id_inventario") int idInventory);

    @POST("addFoodInventory")
    Call<UpdateInsertMsg> addFoodInventory(@Query("id_inventario") int idInventory, @Query("id_alimento") int idAlimento,
                                           @Query("grammi") int grammi, @Query("data_scadenza") String dataScadenza,
                                           @Query("essenziale") boolean essenziale);

    @GET("user")
    Call<Utente> getUser(@Query("id_utente") int idUtente);

}