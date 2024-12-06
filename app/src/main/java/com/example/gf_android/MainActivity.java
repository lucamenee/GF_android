package com.example.gf_android;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gf_android.Api.*;
import com.example.gf_android.Api.Types.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnUpdatesListener {

    List<Alimento> inventario;
    List<Alimento> allAlimenti;
    List<Ricetta> suggestedRecipes;
    Intent intent;
    int idUtente;
    static int idInventario;
    Button btnSuggerisci;
    TextView helloMsg;

    /* inventario */
    RecyclerView recyclerView;
    AlimentoAdapter adapter;



    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSuggerisci = findViewById(R.id.btn_suggerisci);
        helloMsg = findViewById(R.id.hello_msg);

        //app's intro player
        mediaPlayer = MediaPlayer.create(this, R.raw.app_intro);
        mediaPlayer.start();


        // getting user info from loginActivity intent
        intent = getIntent();
        idUtente = intent.getIntExtra("id_utente", 0);
        idInventario = intent.getIntExtra("id_inventario", 0);
        Log.i("MainActivity", "id_utente: " + idUtente + ", id_inventario: " + idInventario);
        // TODO: forse bisogna mettere controllo che info da intent siano state ottenute correttamente


        allAlimenti = Api.getAlimenti();
        inventario = Api.inventory(idInventario);
        helloMsg.setText("Ciao @" + Api.getUser(idUtente).username);

        // TODO: showing food in user inventory
        /*recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3 colonne

        adapter = new AlimentoAdapter(this, inventario, Api.getUser(idUtente));
        recyclerView.setAdapter(adapter);


        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int numberOfColumns = 3;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(numberOfColumns, screenWidth, true));
*/
        GridView gridView = findViewById(R.id.gridview);
        adapter = new AlimentoAdapter(this, inventario, Api.getUser(idUtente));
        gridView.setAdapter(adapter);

        // suggesting recipes
        btnSuggerisci.setOnClickListener(view -> {
            FragmentManager fragmentRecipes = getSupportFragmentManager();
            RecipesPopUp recipesPopUp = new RecipesPopUp();
            recipesPopUp.setData(idInventario);
            recipesPopUp.show(fragmentRecipes, "recipesPopUp");
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_Alimento);
        fab.setOnClickListener(v -> AddAlimento.showAddProductDialog(MainActivity.this, this));



    }



    @Override
    public void onUpdate() {
        inventario = Api.inventory(idInventario);
        adapter.updateData(this.inventario);
    }


}