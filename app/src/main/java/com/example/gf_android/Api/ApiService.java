package com.example.gf_android.Api;

import com.example.gf_android.Api.Types.Alimento;
import com.example.gf_android.Api.Types.LoginResponse;
import com.example.gf_android.Api.Types.ObiettivoSettimana;
import com.example.gf_android.Api.Types.ResponseMsg;
import com.example.gf_android.Api.Types.Ricetta;
import com.example.gf_android.Api.Types.Tag;
import com.example.gf_android.Api.Types.UpdateInsertMsg;
import com.example.gf_android.Api.Types.Utente;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Date;
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
                                           @Query("grammi") int grammi, @Query("data_scadenza") Date dataScadenza,
                                           @Query("essenziale") boolean essenziale);

    @GET("user")
    Call<Utente> getUser(@Query("id_utente") int idUtente);

    @GET("daysGoalRecahed")
    Call<List<ObiettivoSettimana>> getObiettivoPerGiorno(@Query("id_utente") int idUtente);

    @POST("updateUserInfo")
    Call<UpdateInsertMsg> updateUserInfo(@Query("id_utente") int idUtente, @Query("obiettivo_kcal") int obiettivo_kcal, @Query("mail") String email, @Query("id_inventario") int id_inventario);

    @POST("updateFoodQt")
    Call<UpdateInsertMsg> updateFoodQt(@Query("id_riga") int idRigaInventario, @Query("qt") int grammi);

    @POST("updateFoodExpire")
    Call<UpdateInsertMsg> updateFoodExpire(@Query("id_riga") int idRigaInventario, @Query("data") Date dataScadenza);

    @POST("consumeFood")
    Call<UpdateInsertMsg> consumeFood(@Query("id_riga") int idRigaInventario, @Query("qt") int grammi, @Query("id_utente") int idUtente);

    @GET("suggestRecipes")
    Call<List<Ricetta>> suggestRecipes(@Query("id_inventario") int idInventario);

    @GET("getUsersInInventory")
    Call<List<Utente>> getUsersInInventory(@Query("id_inventario") int idInventario);
}