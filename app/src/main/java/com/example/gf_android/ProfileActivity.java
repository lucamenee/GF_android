package com.example.gf_android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gf_android.Api.Api;

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
        TextView userIdText = findViewById(R.id.username_id);
        ProgressBar progressBar = findViewById(R.id.today_goal_progress);
        TextView minProgressText = findViewById(R.id.progress_min);
        TextView maxProgressText = findViewById(R.id.progress_max);
        TextView weeklySummaryText = findViewById(R.id.weekly_summary_text);


        String userName = Api.getUser(idUtente).username;
        String userId = "lollo";
        int dailyGoal = 2000;
        int currentProgress = 1000;
        int weeklyAchievements = 4;

        usernameText.setText(userName);
        userIdText.setText(userId);

        progressBar.setMax(dailyGoal);
        progressBar.setProgress(currentProgress);
        minProgressText.setText("0");
        maxProgressText.setText(String.valueOf(dailyGoal));

        weeklySummaryText.setText("Hai raggiunto il tuo obiettivo " + weeklyAchievements + " volte negli ultimi 7 giorni!");

        profileImage.setImageResource(R.drawable.ic_profile_img);

        profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id_utente", idUtente);
            finish();
        });
    }
}
