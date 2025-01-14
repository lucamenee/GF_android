package com.example.gf_android.Api;

import android.util.Log;

import com.example.gf_android.Api.Types.Alimento;
import com.example.gf_android.Api.Types.LoginResponse;
import com.example.gf_android.Api.Types.ObiettivoSettimana;
import com.example.gf_android.Api.Types.RegistrationResponse;
import com.example.gf_android.Api.Types.ResponseMsg;
import com.example.gf_android.Api.Types.Ricetta;
import com.example.gf_android.Api.Types.SearchInventoryMsg;
import com.example.gf_android.Api.Types.Tag;
import com.example.gf_android.Api.Types.UpdateInsertMsg;
import com.example.gf_android.Api.Types.Utente;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
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

    public static List<Alimento> getAlimenti() {
        Call<List<Alimento>> call = apiService.getAlimenti();
        return getApiResponse(call);
    }

    public static LoginResponse login(String username, String password) {
        Call<LoginResponse> call = apiService.login(username, password);
        return getApiResponse(call);
    }

    public static RegistrationResponse register(String username, String password, String email, int kcal) {
        Call<RegistrationResponse> call = apiService.register(username, password, email, kcal);
        return getApiResponse(call);
    }

    public static List<Alimento> inventory(int idInventory) {
        Call<List<Alimento>> call = apiService.inventory(idInventory);
        return getApiResponse(call);
    }

    public static UpdateInsertMsg addFoodInventory(int idInventory, int idAlimento, int grammi, Date dataScadenza) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Call<UpdateInsertMsg> call = apiService.addFoodInventory(idInventory, idAlimento, grammi, dateFormat.format(dataScadenza));
        UpdateInsertMsg updateInsertMsg = getApiResponse(call);
        if (updateInsertMsg != null) {
            return updateInsertMsg;
        }
        return new UpdateInsertMsg( "Error, food wasn't added to inventory", 0);
    }

    public static Utente getUser(int idUtente) {
        Call<Utente> call = apiService.getUser(idUtente);
        return getApiResponse(call);
    }

    public static List<Ricetta> suggestRecepies(int idInventario) {
        Call<List<Ricetta>> call = apiService.suggestRecipes(idInventario);
        return getApiResponse(call);
    }

    public static UpdateInsertMsg consumeFood(int idRigaInventario, int grammi, int idUtente) {
        Call<UpdateInsertMsg> call = apiService.consumeFood(idRigaInventario, grammi, idUtente);
        return getApiResponse(call);
    }

    // for alimento of idRigaInventario it adds "grammi" grams to it
    public static UpdateInsertMsg updateFoodQt(int idRigaInventario, int grammi) {
        Call<UpdateInsertMsg> call = apiService.updateFoodQt(idRigaInventario, grammi);
        return getApiResponse(call);
    }

    public static List<Utente> getUsersInInventory(int idInventario) {
        Call<List<Utente>> call = apiService.getUsersInInventory(idInventario);
        return getApiResponse(call);
    }

    public static List<ObiettivoSettimana> getObiettivoPerGiorno(int idUtente) { // da controllare
        Call<List<ObiettivoSettimana>> call = apiService.getObiettivoPerGiorno(idUtente);
        return getApiResponse(call);
    }

    public static UpdateInsertMsg updateUserInfo(int idUtente, int obiettivoKcal, String email, int idInventario) {
        Call<UpdateInsertMsg> call = apiService.updateUserInfo(idUtente, obiettivoKcal, email, idInventario);
        return getApiResponse(call);
    }

    public static UpdateInsertMsg updateFoodExpire(int idRigaInventario, Date dataScadenza) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Call<UpdateInsertMsg> call = apiService.updateFoodExpire(idRigaInventario, dateFormat.format(dataScadenza));
        UpdateInsertMsg updateInsertMsg = getApiResponse(call);
        if (updateInsertMsg != null) {
            return updateInsertMsg;
        }
        return new UpdateInsertMsg( "Error, food wasn't added to inventory", 0);
    }

    public static Integer userTodaysCalories(int idUtente) {
        Call<Integer> call = apiService.userTodaysCalories(idUtente);
        return getApiResponse(call);
    }

    public static SearchInventoryMsg searchInventory(String username) {
        Call<SearchInventoryMsg> call = apiService.searchInventory(username);
        return getApiResponse(call);
    }





}
