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
import com.example.gf_android.Api.Types.RegistrationResponse;

public class RegistrationActivity extends AppCompatActivity {

    private EditText emailEditText, usernameEditText, passwordEditText, obiettivoEditText;
    private Button registerButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerButton = findViewById(R.id.registerButton);

        backButton = findViewById(R.id.backToLoginButton);
        backButton.setOnClickListener( v -> finish() );

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

            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || ob.isEmpty())
            {
                Toast.makeText(RegistrationActivity.this, "Tutti i campi devono essere compilati!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                obiettivo = Integer.parseInt(ob);
                RegistrationResponse r = Api.register(username, password, email, obiettivo);

                if (r == null)
                {
                    Toast.makeText(RegistrationActivity.this, "Errore: risposta del server non valida.", Toast.LENGTH_SHORT).show();
                    Log.i("RegistrationActivity", "Api.register ha restituito null");
                }
                else if(r.status > 199 && r.status < 300)
                {
                    Toast.makeText(RegistrationActivity.this, "Registrazione completata!", Toast.LENGTH_SHORT).show();


                    emailEditText.setText("");
                    usernameEditText.setText("");
                    passwordEditText.setText("");
                    obiettivoEditText.setText("");

                    LoginResponse lr = Api.login(username, password);
                    if (lr != null)
                    {
                        startActivity(new Intent(this, MainActivity.class)
                                .putExtra("id_utente", lr.id_utente)
                                .putExtra("id_inventario", lr.id_inventario));
                        finish();
                    }
                    else
                    {
                        Log.i("RegistrationActivity", "Errore lr null");
                        Toast.makeText(RegistrationActivity.this, "Login fallito!", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(r.status > 299 && r.status < 400)
                {
                    usernameEditText.setText("");
                    Toast.makeText(RegistrationActivity.this, r.msg, Toast.LENGTH_SHORT).show();
                    Log.i("RegistrationActivity", r.status + " " + r.msg);
                }
                else Toast.makeText(RegistrationActivity.this, r.msg, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
