package com.example.gf_android;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gf_android.Api.*;
import com.example.gf_android.Api.Types.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<Tag> ls;
    List<Alimenti> la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView hello = findViewById(R.id.hello);


//        ls = Api.getTags();
//        hello.setOnClickListener(v -> {
//            for (Tag tag : ls) {
//                Log.i("MainActivity", tag.id_tag + " " + tag.nome_tag);
//            }
//        });

//        la = Api.getAlimenti();
//        hello.setOnClickListener(v -> {
//            for (Alimenti alimento : la) {
//                Log.i("MainActivity", alimento.id_alimento + " " + alimento.nome_alimento + " " + alimento.id_cat + " " + alimento.img + " " + alimento.kcal + " " + alimento.peso_unitario + " " + alimento.nome_categoria + " " + alimento.durata_media);
//            }
//        });


//        LoginResponse lr = Api.login("test", "test");
//        hello.setOnClickListener(v -> {
//            Log.i("MainActivity", lr.msg + " " + lr.id_utente);
//        });

        String msg = Api.register("test3", "test3", "test3", 1000);
        hello.setOnClickListener(v -> {
            Log.i("MainActivity", "msg:" + msg);
        });



    }


}