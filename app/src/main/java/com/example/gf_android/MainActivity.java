package com.example.gf_android;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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
        Intent intent = getIntent();
        idUtente = intent.getIntExtra("id_utente", 0);
        idInventario = intent.getIntExtra("id_inventario", 0);

        if (idUtente < 0 || idInventario < 0)
        {
            Log.i("MainActivity", "Dati mancanti o non validi nell'Intent. id_utente=" + idUtente + ", id_inventario=" + idInventario);
            Toast.makeText(this, "Errore durante il caricamento", Toast.LENGTH_SHORT).show();
            finish(); // Termina l'attività
        }
        else
        {
            Log.i("MainActivity", "Loggato con id_utente=" + idUtente + " e id_inventario=" + idInventario);
        }


        allAlimenti = Api.getAlimenti();
        inventario = Api.inventory(idInventario);
        helloMsg.setText("Ciao " + Api.getUser(idUtente).username);

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

        ImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setImageResource(R.drawable.ic_profile_img);
        profileImage.setOnClickListener(view -> {
            Intent profileintent = new Intent(MainActivity.this, ProfileActivity.class);
            profileintent.putExtra("id_utente", idUtente);
            startActivity(profileintent);
        });

        FloatingActionButton fab = findViewById(R.id.fab_add_Alimento);
        fab.setOnClickListener(v -> AddAlimento.showAddProductDialog(MainActivity.this, this));

        FloatingActionButton fab_filter = findViewById(R.id.fab_filter_Alimento);
        fab_filter.setOnClickListener(v -> OrdinamentActivity.showOrderDialog(MainActivity.this, this));


    }



    @Override
    public void onUpdate() {
        inventario = Api.inventory(idInventario);
        adapter.updateData(this.inventario);
    }


    @Override
    public void onUpdate(List<Alimento> updatedAlimenti) {
        inventario = updatedAlimenti;
        adapter.updateData(this.inventario);
    }



}