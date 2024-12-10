package com.example.gf_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gf_android.Api.Api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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



        ImageView profileImage = findViewById(R.id.profile_image);
        TextView usernameText = findViewById(R.id.username_text);
        TextView useremail = findViewById(R.id.username_id);
        ProgressBar progressBar = findViewById(R.id.today_goal_progress);
        TextView minProgressText = findViewById(R.id.progress_min);
        TextView maxProgressText = findViewById(R.id.progress_max);
        LinearLayout weeklySummaryText = findViewById(R.id.weekly_summary_text);

        String userName = Api.getUser(idUtente).username;
        String email = Api.getUser(idUtente).email;
        int dailyGoal = Api.getUser(idUtente).obiettivo_kcal;
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

        //Calcola i giorni della settimana corrente
        String[] weekDays = getWeekDays();

        int goalsAchieved = 0;

        for (String day : weekDays) {
            // Crea dinamicamente un TextView per ogni giorno
            TextView dayView = new TextView(this);
            dayView.setText(day);
            dayView.setTextSize(11);
            dayView.setPadding(16, 8, 16, 8);


            boolean isAchieved = Math.random() > 0.5; // TODO: Sostituisci con la logica vera e propria
            if (isAchieved) {
                dayView.setText(day + " ✓");
                goalsAchieved++;
            }

            // Aggiungi il TextView al LinearLayout
            weeklySummaryText.addView(dayView);
        }


        TextView summaryText = findViewById(R.id.summary_text);
        summaryText.setText(String.format(
                Locale.getDefault(),
                "Hai raggiunto il tuo obiettivo %d volte negli ultimi 7 giorni!",
                goalsAchieved
        ));
    }

    // Metodo per ottenere i giorni della settimana corrente
    private String[] getWeekDays() {
        String[] weekDays = new String[7];
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd", Locale.ITALIAN);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        for (int i = 0; i < 7; i++) {
            weekDays[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return weekDays;
    }

}
