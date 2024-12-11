package com.example.gf_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.ObiettivoSettimana;
import com.example.gf_android.Api.Types.Utente;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    Intent intent;
    int idUtente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_manager);

        intent = getIntent();
        idUtente = intent.getIntExtra("id_utente", 0);


        List<ObiettivoSettimana> obiettiviSettimana = Api.getObiettivoPerGiorno(idUtente);
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView usernameText = findViewById(R.id.username_text);
        TextView useremail = findViewById(R.id.username_id);
        ProgressBar progressBar = findViewById(R.id.today_goal_progress);
        TextView minProgressText = findViewById(R.id.progress_min);
        TextView maxProgressText = findViewById(R.id.progress_max);


        Utente user = Api.getUser(idUtente);
        String userName = user.username;
        String email = user.email;
        int dailyGoal = user.obiettivo_kcal;
        int currentProgress = 800;

        usernameText.setText(userName);
        useremail.setText(email);

        progressBar.setMax(dailyGoal);
        progressBar.setProgress(currentProgress);
        minProgressText.setText("0");
        maxProgressText.setText(String.valueOf(dailyGoal));


        profileImage.setImageResource(R.drawable.ic_profile_img);

        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id_utente", idUtente);
            finish();
        });


        if (obiettiviSettimana != null) {
            aggiornaVistaSettimana(obiettiviSettimana);
        } else {
            Toast.makeText(this, "Errore nel caricamento dei dati", Toast.LENGTH_SHORT).show();
        }

    }
    private void aggiornaVistaSettimana(List<ObiettivoSettimana> obiettiviSettimana) {
        LinearLayout weekly_summary_text = findViewById(R.id.weekly_summary_text);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd", Locale.ITALIAN);

        for (ObiettivoSettimana obiettivo : obiettiviSettimana) {
            TextView TestoObiettivo = new TextView(this);
            TestoObiettivo.setText(dateFormat.format(obiettivo.data_consumazione));
            TestoObiettivo.setTextSize(14);
            TestoObiettivo.setPadding(16, 8, 16, 8);

            if (obiettivo.obiettivo_raggiunto) {
                TestoObiettivo.setText(TestoObiettivo.getText() + " ✓");
                TestoObiettivo.setTextColor(getColor(R.color.buono));
            }else{
                TestoObiettivo.setTextColor(getColor(R.color.quasiscaduto));
            }

            weekly_summary_text.addView(TestoObiettivo);
        }
        aggiornaRiepilogo(obiettiviSettimana);
    }

    private void aggiornaRiepilogo(List<ObiettivoSettimana> obiettiviSettimana) {
        int obiettiviRaggiunti = 0;

        for (ObiettivoSettimana obiettivo : obiettiviSettimana) {
            if (obiettivo.obiettivo_raggiunto) {
                obiettiviRaggiunti++;
            }
        }

        TextView summaryText = findViewById(R.id.summary_text);
        summaryText.setText("Hai raggiunto il tuo obiettivo " + obiettiviRaggiunti + " volte negli ultimi 7 giorni!");
    }
}
