package com.example.gf_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.LoginResponse;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText, obiettivoEditText;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener( v -> {

            emailEditText = findViewById(R.id.email);
            usernameEditText = findViewById(R.id.username);
            passwordEditText = findViewById(R.id.password);
            obiettivoEditText = findViewById(R.id.obiettivocal);

            String email = emailEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String ob = obiettivoEditText.getText().toString();
            int obiettivo = 0;

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || ob.isEmpty()) {
                Toast.makeText(RegistrationActivity.this, "Tutti i campi devono essere compilati!", Toast.LENGTH_SHORT).show();
            } else {
                obiettivo = Integer.parseInt(ob);
                String r = Api.register(username, password, email, obiettivo);

                if(!r.equals("Error, user wasn't registered")) {
                    Toast.makeText(RegistrationActivity.this, "Registrazione completata!", Toast.LENGTH_SHORT).show();

                    // Pulisci i campi dopo la registrazione
                    emailEditText.setText("");
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    obiettivoEditText.setText("");

                    LoginResponse lr = Api.login(username, password);
                    if (lr != null) {
                        startActivity(new Intent(this, MainActivity.class)
                                .putExtra("id_utente", lr.id_utente)
                                .putExtra("id_inventario", lr.id_inventario));
                        finish();
                    } else {
                        // Se login fallisce, logga il problema
                        Log.i("RegistrationActivity", "Errore, per qualche motivo le credenziali sono sbagliate");
                        Toast.makeText(RegistrationActivity.this, "Login fallito!", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(RegistrationActivity.this, "Errore! Non registrato", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
