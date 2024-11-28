package com.example.gf_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.gf_android.Api.*;
import com.example.gf_android.Api.Types.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Alimento> inventario;
    List<Alimento> allAlimenti;
    List<Ricetta> suggestedRecipes;
    Intent intent;
    int idUtente;
    int idInventario;
    Button btnSuggerisci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSuggerisci = findViewById(R.id.btn_suggerisci);

        // getting user info from loginActivity intent
        intent = getIntent();
        idUtente = intent.getIntExtra("id_utente", 0);
        idInventario = intent.getIntExtra("id_inventario", 0);
        Log.i("MainActivity", "id_utente: " + idUtente + ", id_inventario: " + idInventario);
        // TODO: forse bisogna mettere controllo che info da intent siano state ottenute correttamente


        allAlimenti = Api.getAlimenti();
        inventario = Api.inventory(idInventario);

        // TODO: showing food in user inventory


        // suggesting recipes
        btnSuggerisci.setOnClickListener(view -> {
            FragmentManager fragmentRecipes = getSupportFragmentManager();
            RecipesPopUp recipesPopUp = new RecipesPopUp();
            recipesPopUp.setData(idInventario);
            recipesPopUp.show(fragmentRecipes, "recipesPopUp");
        });





    }


}