package com.example.gf_android;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton$InspectionCompanion;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.ObiettivoSettimana;
import com.example.gf_android.Api.Types.SearchInventoryMsg;
import com.example.gf_android.Api.Types.UpdateInsertMsg;
import com.example.gf_android.Api.Types.Utente;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    Intent intent;
    int idUtente;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_manager);

        intent = getIntent();
        idUtente = intent.getIntExtra("id_utente", 0);

        backButton = findViewById(R.id.backToInventoryButton);
        backButton.setOnClickListener( v -> finish() );


        List<ObiettivoSettimana> obiettiviSettimana = Api.getObiettivoPerGiorno(idUtente);
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView usernameText = findViewById(R.id.username_text);
        TextView useremail = findViewById(R.id.username_id);
        ProgressBar progressBar = findViewById(R.id.today_goal_progress);
        TextView minProgressText = findViewById(R.id.progress_min);
        TextView maxProgressText = findViewById(R.id.progress_max);
        Button profileEdit = findViewById(R.id.profile_edit);
        TextView inventoryMsg = findViewById(R.id.inventory_msg);
        Button changeInvBtn = findViewById(R.id.change_btn);


        profileEdit.setOnClickListener(v -> showEditProfileDialog());

        Utente user = Api.getUser(idUtente);
        String userName = user.username;
        String email = user.email;
        int dailyGoal = user.obiettivo_kcal;
        int currentProgress = Api.userTodaysCalories(idUtente);

        if(email == null) Log.i("ProfileActivity", "errore email null");
        usernameText.setText(userName);
        useremail.setText(email);

        progressBar.setMax(dailyGoal);
        progressBar.setProgress(currentProgress);
        minProgressText.setText("0");
        maxProgressText.setText(String.valueOf(dailyGoal));

        inventoryMsg.setText("Stai convidendo l'inventario con " + user.proprietario);
        changeInvBtn.setOnClickListener(v -> {
            TextView ownerUsernameTxt = findViewById(R.id.username_owner);
            String ownerUsername = ownerUsernameTxt.getText().toString();
            SearchInventoryMsg siMsg = Api.searchInventory(ownerUsername);

            if (siMsg.code >= 300 && siMsg.code < 400) {
                UpdateInsertMsg upMsg = Api.updateUserInfo(user.id_utente, user.obiettivo_kcal, user.email, siMsg.id_inventario_og);
                startActivity(new Intent(this, MainActivity.class).putExtra("id_utente", user.id_utente).putExtra( "id_inventario", siMsg.id_inventario_og));

                Toast.makeText(this, "Aggiornamento effettuato", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, siMsg.msg, Toast.LENGTH_SHORT).show();
            }
        });




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

    /*private void aggiornaVistaSettimana(List<ObiettivoSettimana> obiettiviSettimana) {
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
    }*/

    private void aggiornaVistaSettimana(List<ObiettivoSettimana> obiettiviSettimana) {

        LinearLayout weeklyResultsCircles = findViewById(R.id.weekly_results_circles);

        // Pulisci eventuali vecchi cerchi
        weeklyResultsCircles.removeAllViews();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", Locale.ITALIAN);

        for (ObiettivoSettimana obiettivo : obiettiviSettimana) {
            TextView circleView = new TextView(this);

            // Ottieni le prime tre lettere del giorno
            String dayAbbreviation = dateFormat.format(obiettivo.data_consumazione).toUpperCase();

            // Configura il cerchio
            circleView.setText(dayAbbreviation);
            circleView.setTextSize(16); // Dimensione del testo
            circleView.setTextColor(getColor(R.color.white));
            circleView.setGravity(Gravity.CENTER); // Centra il testo verticalmente e orizzontalmente
            circleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // Assicura l'allineamento

            // Colore del cerchio (sfondo)
            if (obiettivo.obiettivo_raggiunto) {
                circleView.setBackgroundResource(R.drawable.green_circle); // Cerchio verde
            } else {
                circleView.setBackgroundResource(R.drawable.blue_circle); // Cerchio blu
            }

            // Configura le dimensioni e il margine del cerchio
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    120, 120 // Dimensioni fisse del cerchio (120x120 dp)
            );
            params.setMargins(8, 0, 8, 0); // Margini tra i cerchi
            circleView.setLayoutParams(params);

            // Aggiungi il cerchio al layout
            weeklyResultsCircles.addView(circleView);
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

    private void showEditProfileDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);

        EditText editObiettivo = dialogView.findViewById(R.id.editObiettivo);
        EditText editEmail = dialogView.findViewById(R.id.edit_email);


        // Prefill the fields with existing data (optional)
        Utente user = Api.getUser(idUtente);

        editObiettivo.setText(user.obiettivo_kcal + "");
        editEmail.setText(user.email);


        new AlertDialog.Builder(this)
                .setTitle("Edit Profile")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newObiettivo =editObiettivo.getText().toString().trim();
                    String newEmail = editEmail.getText().toString().trim();
                    //String newPassword = editPassword.getText().toString().trim();


                    saveProfileChanges(newEmail, newObiettivo);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void saveProfileChanges(String email, String obiettivo) {
        TextView maxProgressText = findViewById(R.id.progress_max);

        maxProgressText.setText(obiettivo);

        int obiettivoInt = Integer.parseInt(obiettivo);

        Utente user = Api.getUser(idUtente);

        Api.updateUserInfo(idUtente, obiettivoInt, email, user.id_inventario);
    }
}
