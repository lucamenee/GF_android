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

        ls = Api.getTags();
        TextView hello = findViewById(R.id.hello);
        hello.setOnClickListener(v -> {
            if (ls == null) {
                Log.i("MainActivity", "null");
            }
            for (Tag tag : ls) {
                Log.i("MainActivity", tag.id_tag + " " + tag.nome_tag);
            }
        });


    }


}